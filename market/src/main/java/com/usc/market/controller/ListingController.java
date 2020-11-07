package com.usc.market.controller;

import com.usc.market.vo.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ListingController
 * Usage:
 *  Operation for Listing Item.
 */
@RequestMapping("/listing")
public class ListingController {

    @GetMapping("/addToListing")
    public Response<String> addToListing() {
        return Response.ok();
    }

}
