package com.usc.market.controller;

import com.usc.market.util.JwtTokenUtil;
import com.usc.market.model.AuthEntity;
import com.usc.market.model.UserEntity;
import com.usc.market.repo.AuthorizationRepository;
import com.usc.market.repo.UserRepository;
import com.usc.market.vo.Credential;
import com.usc.market.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * AuthController
 * Usage:
 *  Authentication Controller
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthorizationRepository authorizationRepository;

    private final UserRepository userRepository;

    @Autowired
    private JwtTokenUtil tokenUtil;

    public AuthController(AuthorizationRepository authorizationRepository, UserRepository userRepository) {
        this.authorizationRepository = authorizationRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public Response<String> login(@RequestBody Credential user) {
        System.out.println(user.getUsername() + " " + user.getPassword());
        UserEntity matcher = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (matcher == null) {
             return Response.fail("Wrong Username/Password.");
        }

        String token = tokenUtil.generateToken(new User(user.getUsername(), user.getPassword(), Collections.emptyList()));
        // persist to db
        authorizationRepository.save(new AuthEntity(user.getUsername(), token));

        return Response.ok(token);
    }

    @PostMapping("/register")
    public Response<String> register(@RequestBody UserEntity user) {
        user.setId(0);
        user.setGmtUpdate(System.currentTimeMillis());

        if (user.getUscId().isBlank() || user.getEmail().isBlank() || user.getPassword().isBlank() || user.getRealName().isBlank() || user.getUsername().isBlank()) {
            return Response.fail("Please fill all information!");
        }

        if (!user.getEmail().endsWith("@usc.edu")) {
            return Response.fail("Invalid email.");
        }

        if (userRepository.findByUsername(user.getUsername()) != null) {
            return Response.fail("Duplicate username.");
        }

        if (userRepository.findByUscId(user.getUscId()) != null) {
            return Response.fail("Duplicate USC-ID");
        }

        userRepository.saveAndFlush(user);

        return Response.ok("Register successfully.");
    }

}
