package com.usc.market.controller;

import com.usc.market.common.PickupLoc;
import com.usc.market.common.Quality;
import com.usc.market.model.FavoriteListingEntity;
import com.usc.market.model.FavoriteListingEntityPK;
import com.usc.market.model.ListingEntity;
import com.usc.market.model.UserEntity;
import com.usc.market.repo.FavoriteListingRepository;
import com.usc.market.repo.ListingRepository;
import com.usc.market.repo.UserRepository;
import com.usc.market.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * ListingController
 * Usage:
 * Operation for Listing Item.
 */
@RestController
@RequestMapping("/listing")
public class ListingController {

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FavoriteListingRepository favoriteListingRepository;

    @GetMapping("/findAll")
    public Response<List<ListingEntity>> findAllListing() {
        return Response.ok(listingRepository.findAllByOrderByIdDesc().stream().peek(
                data -> {
                    // clear user info
                    UserEntity user = data.getUserByOwnerId();
                    user.setListingsById(null);
                    user.setPassword(null);
                    user.setFavoriteListingsById(null);
                }
        ).collect(Collectors.toList()));
    }

    @GetMapping("/addToFavorite")
    public Response<String> addListingToFavorite(@RequestHeader int id, @RequestParam int listingId) {
        if (listingRepository.findById(listingId).isEmpty()) {
            return Response.ok("No such listing.");
        }
        if (favoriteListingRepository.findById(new FavoriteListingEntityPK(id, listingId)).isPresent()) {
            return Response.ok("Listing in your favorite list already.");
        }
        FavoriteListingEntity data = new FavoriteListingEntity();
        data.setUserId(id);
        data.setListingId(listingId);
        data.setGmtCreate(System.currentTimeMillis());
        favoriteListingRepository.saveAndFlush(data);
        return Response.ok("Add to your favorite list successfully.");
    }

    @GetMapping("/removeFromFavorite")
    public Response<String> removeListingFromFavorite(@RequestHeader int id, @RequestParam int listingId) {
        FavoriteListingEntityPK data = new FavoriteListingEntityPK();
        data.setUserId(id);
        data.setListingId(listingId);
        favoriteListingRepository.deleteById(data);
        return Response.ok("Move item out of your favorite list successfully.");
    }

    @GetMapping("/myListing")
    public Response<List<ListingEntity>> getMyOwnListing(@RequestHeader int id) {
        return Response.ok(userRepository.findById(id).get().getListingsById().stream().peek(
                data -> {
                    // clear user info
                    UserEntity user = data.getUserByOwnerId();
                    user.setListingsById(null);
                    user.setPassword(null);
                    user.setFavoriteListingsById(null);
                }
        ).collect(Collectors.toList()));
    }

    @GetMapping("/myFavorite")
    public Response<List<ListingEntity>> getMyFavoriteListings(@RequestHeader int id) {
        return Response.ok(favoriteListingRepository.findAllByUserIdOrderByGmtCreateDesc(id).stream().map(
                x -> {
                    ListingEntity data = x.getListingByListingId();
                    // clear user info
                    UserEntity user = data.getUserByOwnerId();
                    user.setListingsById(null);
                    user.setPassword(null);
                    user.setFavoriteListingsById(null);
                    return data;
                }
        ).collect(Collectors.toList()));
    }

    @PostMapping("/addListingItem")
    public Response<String> addListingItem(@RequestHeader int id, @RequestBody ListingEntity data) {
        if (data.getImage().isBlank() || data.getItemDescription().isBlank() || data.getItemName().isBlank()) {
            return Response.fail("Information not complete.");
        }
        if (!PickupLoc.isValid(data.getPickupLoc())) {
            return Response.fail("Invalid pickup location");
        }
        if (!Quality.isValid(data.getQuality())) {
            return Response.fail("Invalid quality");
        }
        data.setId(0);
        data.setSold((byte) 0);
        UserEntity user = userRepository.findById(id).get();
        data.setUserByOwnerId(user);
        listingRepository.saveAndFlush(data);
        return Response.ok("Add listing successfully");
    }

    @PostMapping("/updateListingItem")
    public Response<String> updateListingItem(@RequestHeader int id, @RequestBody ListingEntity data) {
        if (data.getImage().isBlank() || data.getItemDescription().isBlank() || data.getItemName().isBlank()) {
            return Response.fail("Information not complete.");
        }
        if (!PickupLoc.isValid(data.getPickupLoc())) {
            return Response.fail("Invalid pickup location");
        }
        if (!Quality.isValid(data.getQuality())) {
            return Response.fail("Invalid quality");
        }
        Optional<ListingEntity> rawOpt = listingRepository.findById(data.getId());
        if (rawOpt.isEmpty()) {
            return Response.fail("No such listing.");
        }

        ListingEntity raw = rawOpt.get();
        if (raw.getUserByOwnerId().getId() != id) {
            return Response.fail("Unable to modify other's listing.");
        }

        raw.setItemName(data.getItemName());;
        raw.setItemDescription(data.getItemDescription());
        raw.setImage(data.getImage());
        raw.setQuantity(data.getQuantity());
        raw.setPickupLoc(data.getPickupLoc());
        raw.setSold(data.getSold());
        raw.setHeld(data.getHeld());

        listingRepository.saveAndFlush(raw);

        return Response.ok("Modify listing successfully");
    }

    @GetMapping("/deleteListingItem")
    public Response<String> deleteListingItem(@RequestHeader int id, @RequestParam int listing) {
        Optional<ListingEntity> rawOpt = listingRepository.findById(listing);
        if (rawOpt.isEmpty()) {
            return Response.ok("Delete successfully.");
        }

        ListingEntity raw = rawOpt.get();
        if (raw.getUserByOwnerId().getId() != id) {
            return Response.fail("Unable to delete other's listing.");
        }
        listingRepository.deleteById(listing);
        return Response.ok("Delete successfully.");
    }


}
