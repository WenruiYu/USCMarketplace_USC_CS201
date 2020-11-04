package com.usc.market.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserVO {

    private int id;

    private String username;

    private String password;

    private String tel;

    private String token;

}
