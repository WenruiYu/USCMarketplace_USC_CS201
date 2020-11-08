package com.usc.market.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auth", schema = "market", catalog = "")
public class AuthEntity {
    private String username;
    private String token;

    /**
     * get username
     *
     * @return username
     */
    @Id
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    /**
     * set username
     *
     * @param username username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * get token
     *
     * @return token
     */
    @Basic
    @Column(name = "token")
    public String getToken() {
        return token;
    }

    /**
     * set token
     *
     * @param token token
     */
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthEntity that = (AuthEntity) o;

        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (token != null ? !token.equals(that.token) : that.token != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (token != null ? token.hashCode() : 0);
        return result;
    }
}
