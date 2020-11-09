package com.usc.market.model;

import javax.persistence.*;

@Entity
@Table(name = "favorite_listing")
@IdClass(FavoriteListingEntityPK.class)
public class FavoriteListingEntity{
    private int userId;
    private int listingId;
    private long gmtCreate;
    private UserEntity userByUserId;
    private ListingEntity listingByListingId;

    /**
     * get userId
     *
     * @return userId
     */
    @Id
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    /**
     * set userId
     *
     * @param userId userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * get listingId
     *
     * @return listingId
     */
    @Id
    @Column(name = "listing_id")
    public int getListingId() {
        return listingId;
    }

    /**
     * set listingId
     *
     * @param listingId listingId
     */
    public void setListingId(int listingId) {
        this.listingId = listingId;
    }

    /**
     * get gmtCreate
     *
     * @return gmtCreate
     */
    @Basic
    @Column(name = "gmt_create")
    public long getGmtCreate() {
        return gmtCreate;
    }

    /**
     * set gmtCreate
     *
     * @param gmtCreate gmtCreate
     */
    public void setGmtCreate(long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FavoriteListingEntity that = (FavoriteListingEntity) o;

        if (userId != that.userId) return false;
        if (listingId != that.listingId) return false;
        if (gmtCreate != that.gmtCreate ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + listingId;
        result = 31 * result + (int) (gmtCreate ^ (gmtCreate >>> 32));;
        return result;
    }

    /**
     * get userByUserId
     *
     * @return userByUserId
     */
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public UserEntity getUserByUserId() {
        return userByUserId;
    }

    /**
     * set userByUserId
     *
     * @param userByUserId userByUserId
     */
    public void setUserByUserId(UserEntity userByUserId) {
        this.userByUserId = userByUserId;
    }

    /**
     * get listingByListingId
     *
     * @return listingByListingId
     */
    @ManyToOne
    @JoinColumn(name = "listing_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public ListingEntity getListingByListingId() {
        return listingByListingId;
    }

    /**
     * set listingByListingId
     *
     * @param listingByListingId listingByListingId
     */
    public void setListingByListingId(ListingEntity listingByListingId) {
        this.listingByListingId = listingByListingId;
    }
}
