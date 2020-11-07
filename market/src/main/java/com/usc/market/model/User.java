package com.usc.market.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * User
 * Usage:
 *
 * @author heleninsa
 * create time 2020/11/1 - 10:12 PM
 */
@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    private int id;

    private String username;

    private String password;

    private String tel;

    private Timestamp gmtUpdate;


}
