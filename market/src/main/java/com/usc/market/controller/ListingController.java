package com.usc.market.controller;

import com.usc.market.vo.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ListingController
 * Usage:
 *  Operation for Listing Item.
 */
@RestController
@RequestMapping("/listing")
public class ListingController {

    @GetMapping("/addToListing")
    public Response<String> addToListing() {
        return Response.ok();
    }

}
