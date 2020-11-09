package com.usc.market.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
public class UserEntity {
    private int id;
    private String username;
    private String password;
    private String realName;
    private String email;
    private String uscId;
    private String mobile;
    private long gmtUpdate;
    private List<FavoriteListingEntity> favoriteListingsById;
    private List<ListingEntity> listingsById;

    /**
     * get id
     *
     * @return id
     */
    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    /**
     * set id
     *
     * @param id id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * get username
     *
     * @return username
     */
    @Basic
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
     * get password
     *
     * @return password
     */
    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    /**
     * set password
     *
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * get realName
     *
     * @return realName
     */
    @Basic
    @Column(name = "real_name")
    public String getRealName() {
        return realName;
    }

    /**
     * set realName
     *
     * @param realName realName
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * get email
     *
     * @return email
     */
    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    /**
     * set email
     *
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * get uscId
     *
     * @return uscId
     */
    @Basic
    @Column(name = "usc_id")
    public String getUscId() {
        return uscId;
    }

    /**
     * set uscId
     *
     * @param uscId uscId
     */
    public void setUscId(String uscId) {
        this.uscId = uscId;
    }

    /**
     * get mobile
     *
     * @return mobile
     */
    @Basic
    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    /**
     * set mobile
     *
     * @param mobile mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * get gmtUpdate
     *
     * @return gmtUpdate
     */
    @Basic
    @Column(name = "gmt_update")
    public long getGmtUpdate() {
        return gmtUpdate;
    }

    /**
     * set gmtUpdate
     *
     * @param gmtUpdate gmtUpdate
     */
    public void setGmtUpdate(long gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (id != that.id) return false;
        if (gmtUpdate != that.gmtUpdate) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (realName != null ? !realName.equals(that.realName) : that.realName != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (uscId != null ? !uscId.equals(that.uscId) : that.uscId != null) return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (realName != null ? realName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (uscId != null ? uscId.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (int) (gmtUpdate ^ (gmtUpdate >>> 32));
        return result;
    }

    /**
     * get favoriteListingsById
     *
     * @return favoriteListingsById
     */
    @OneToMany(mappedBy = "userByUserId")
    @OrderBy("gmtCreate desc")
    @JsonIgnore
    public List<FavoriteListingEntity> getFavoriteListingsById() {
        return favoriteListingsById;
    }

    /**
     * set favoriteListingsById
     *
     * @param favoriteListingsById favoriteListingsById
     */
    public void setFavoriteListingsById(List<FavoriteListingEntity> favoriteListingsById) {
        this.favoriteListingsById = favoriteListingsById;
    }

    /**
     * get listingsById
     *
     * @return listingsById
     */
    @OneToMany(mappedBy = "userByOwnerId")
    @OrderBy("id desc")
    @JsonIgnore
    public List<ListingEntity> getListingsById() {
        return listingsById;
    }

    /**
     * set listingsById
     *
     * @param listingsById listingsById
     */
    public void setListingsById(List<ListingEntity> listingsById) {
        this.listingsById = listingsById;
    }
}
