package com.usc.market.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class FavoriteListingEntityPK implements Serializable {
    private int userId;
    private int listingId;

    public FavoriteListingEntityPK() {
    }

    public FavoriteListingEntityPK(int userId, int listingId) {
        this.userId = userId;
        this.listingId = listingId;
    }

    /**
     * get userId
     *
     * @return userId
     */
//    @Column(name = "user_id")
//    @Id
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
//    @Column(name = "listing_id")
//    @Id
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FavoriteListingEntityPK that = (FavoriteListingEntityPK) o;

        if (userId != that.userId) return false;
        if (listingId != that.listingId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + listingId;
        return result;
    }
}
