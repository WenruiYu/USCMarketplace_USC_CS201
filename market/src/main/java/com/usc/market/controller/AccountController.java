package com.usc.market.controller;

import com.usc.market.model.UserEntity;
import com.usc.market.repo.UserRepository;
import com.usc.market.vo.ModifyPasswordVO;
import com.usc.market.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * AccountController
 * Usage:
 *
 * @author heleninsa
 * create time 2020/11/6 - 12:19 AM
 */
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/getInfo")
    public Response<UserEntity> getInfo(@RequestHeader String username) {
        UserEntity data = userRepository.findByUsername(username);
        data.setPassword("");
        return Response.ok(data);
    }

    @PostMapping("/modifyInfo")
    public Response<String> modifyInfo(@RequestHeader String username, @RequestBody UserEntity user) {
        UserEntity data = userRepository.findByUsername(username);

        user.setGmtUpdate(System.currentTimeMillis());

        if (user.getUscId().isBlank() || user.getEmail().isBlank() || user.getRealName().isBlank()) {
            return Response.fail("Please fill all information!");
        }

        if (!user.getEmail().endsWith("@usc.edu")) {
            return Response.fail("Invalid email.");
        }
        UserEntity dupUscID = userRepository.findByUscId(user.getUscId());

        if (dupUscID != null && dupUscID.getId() != data.getId()) {
            return Response.fail("Duplicate USC-ID");
        }

        user.setId(data.getId());
        user.setUsername(data.getUsername());
        user.setPassword(data.getPassword());
        userRepository.saveAndFlush(user);

        return Response.ok("Modify successfully.");
    }


    @PostMapping("/changePassword")
    public Response<String> changePassword(@RequestHeader String username, @RequestBody ModifyPasswordVO data) {
        String pre = data.getPre();
        String password = data.getCur();
        password = password == null ? "" : password;
        password = password.trim();
        if (password.isBlank()) {
            return Response.fail("Empty password not permit");
        }
        UserEntity user = userRepository.findByUsername(username);
        if (!user.getPassword().equals(pre)) {
            return Response.fail("Password not match.");
        }

        user.setPassword(password);
        userRepository.save(user);

        return Response.ok("Modify successfully.");
    }

}
