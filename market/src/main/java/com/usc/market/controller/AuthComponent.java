package com.usc.market.controller;

import com.usc.market.model.AuthEntity;
import com.usc.market.model.UserEntity;
import com.usc.market.repo.AuthorizationRepository;
import com.usc.market.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;

/**
 * AuthComponent
 * Usage:
 *
 * @author heleninsa
 * create time 2020/10/30 - 3:22 PM
 */
@Component
public class AuthComponent {

    private final AuthorizationRepository repository;

    private final UserRepository userRepository;

    @Autowired
    public AuthComponent(AuthorizationRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public boolean auth(String token, int username) {
        if (null == token || token.isEmpty()) {
            return false;
        } else {
            UserEntity us = userRepository.findById(username).orElse(null);
            if (us == null) {
                return false;
            }

            AuthEntity auth = repository.findByUsername(us.getUsername());

            if (null == auth || !auth.getToken().equals(token)) {
                return false;
            } else {
                long now = new java.util.Date().getTime();
                long tokenTs = Long.parseLong(new String(Base64.getDecoder().decode(token)).split("-")[1]);

                if (tokenTs > now || now - tokenTs > 1209600000) {
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

}
