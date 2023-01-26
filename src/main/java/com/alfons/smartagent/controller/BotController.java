package com.alfons.smartagent.controller;

import com.alfons.smartagent.model.*;
import com.alfons.smartagent.repo.ApartmentRepository;
import com.alfons.smartagent.repo.ApartmentService;
import com.alfons.smartagent.repo.SearchService;
import com.alfons.smartagent.service.Yad2Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/")
public class BotController {

    @Autowired
    ApartmentService apartmentService;

    @Autowired
    SearchService searchService;

    @Autowired
    Yad2Service yad2Service;

    Random random = new Random();
    @Autowired
    private ApartmentRepository apartmentRepository;

    @RequestMapping(value = "/yad2", method = RequestMethod.POST)
    public ResponseEntity<?> getApartments(@RequestBody SearchRequest request) throws IOException {
        ApartmentsFeed apartmentsFeed = yad2Service.searchApartments(request.getCity(), request.getType(), request.getMinimumPrice(), request.getMaximumPrice());
        List<FeedItem> feedItems = apartmentsFeed.getFeed_items();
        String searchId = generateSearchId();
        for (FeedItem feedItem : feedItems) {
            if(feedItem.getId() != null) {
                if(feedItem.getStreet() == null) feedItem.setStreet(request.getCity());
                feedItem.setSearchId(searchId);
                feedItem.setId(feedItem.getId() + searchId);
                request.setSearchId(searchId);
                apartmentService.save(feedItem.toApartment());
            }
        }
        searchService.save(request);
        Map<String, Object> response = new HashMap<>();
        response.put("searchId", searchId);
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = mapper.writeValueAsString(response);
        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/yad2/{searchId}", method = RequestMethod.GET)
    public List<Apartment> getApartments(@PathVariable String searchId) {
        List<Apartment> apartments = apartmentService.findBySearchId(searchId);
        return apartments;
    }


    private String generateSearchId() {
        String charPool = "ABCDEFHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            res.append(charPool.charAt(random.nextInt(charPool.length())));
        }
        return res.toString();
    }
}