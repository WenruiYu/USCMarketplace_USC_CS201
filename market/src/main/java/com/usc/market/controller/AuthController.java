package com.usc.market.controller;

import com.usc.market.model.Authorization;
import com.usc.market.model.User;
import com.usc.market.repo.AuthorizationRepository;
import com.usc.market.repo.UserRepository;
import com.usc.market.vo.Credential;
import com.usc.market.vo.Response;
import com.usc.market.vo.UserVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

/**
 * AuthController
 * Usage:
 *
 * @author heleninsa
 * create time 2020/11/1 - 5:56 下午
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthorizationRepository authorizationRepository;

    private final UserRepository userRepository;

    public AuthController(AuthorizationRepository authorizationRepository, UserRepository userRepository) {
        this.authorizationRepository = authorizationRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public Response<Object> login(@RequestBody Credential user) {
        User matcher = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (matcher == null) {
             return Response.fail("用户名或密码错误。");
        }

        // auth updater.
        long ts = new java.util.Date().getTime();
        String rawToken = user.getUsername() + "-" + ts;

        String token = new String(Base64.getEncoder().encode(rawToken.getBytes()));

        // persist to db
        authorizationRepository.save(new Authorization(user.getUsername(), token));

        return Response.suc(new UserVO(matcher.getId(), matcher.getUsername(), null, matcher.getTel(), token));
    }

}
