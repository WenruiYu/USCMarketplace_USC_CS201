package com.usc.market.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Authorization
 * Usage:
 *
 * @author heleninsa
 * create time 2020/11/1 - 5:43 PM
 */
@Data
@Entity
@Table(name = "auth")
@AllArgsConstructor
@NoArgsConstructor
public class Authorization {

    @Id
    private String username;

    private String token;


}
