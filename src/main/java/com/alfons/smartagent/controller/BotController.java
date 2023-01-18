package com.alfons.smartagent.controller;

import com.alfons.smartagent.model.Apartment;
import com.alfons.smartagent.model.ApartmentsFeed;
import com.alfons.smartagent.model.FeedItem;
import com.alfons.smartagent.model.RealEstate;
import com.alfons.smartagent.repo.ApartmentRepository;
import com.alfons.smartagent.repo.ApartmentService;
import com.alfons.smartagent.service.Yad2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/")
public class BotController {

    @Autowired
    ApartmentService apartmentService;

    @Autowired
    Yad2Service yad2Service;

    Random random = new Random();
    @Autowired
    private ApartmentRepository apartmentRepository;

    @RequestMapping(value = "/yad2", method = RequestMethod.GET)
    public ResponseEntity<?> getApartments(@RequestParam(defaultValue = "rent") RealEstate type, @RequestParam String city, @RequestParam String maximumPrice) throws IOException {
        ApartmentsFeed apartmentsFeed = yad2Service.searchApartments(city, type.toString(), maximumPrice);
        List<FeedItem> feedItems = apartmentsFeed.getFeed_items();
        String searchId = generateSearchId();
        for (FeedItem feedItem : feedItems) {
            if(feedItem.getId() != null) {
                feedItem.setSearchId(searchId);
                apartmentService.save(feedItem.toApartment());
            }
        }
        return new ResponseEntity<>(searchId, HttpStatus.OK);
    }

//    @RequestMapping(value = "/yad2/{searchId}", method = RequestMethod.GET)
//    public List<Apartment> getApartments(@RequestParam String searchId) {
//        List<Apartment> apartments = (List<Apartment>) apartmentRepository.findApartmentsBySearchId(searchId);
//        return apartments;
//    }


    private String generateSearchId() {
        String charPool = "ABCDEFHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            res.append(charPool.charAt(random.nextInt(charPool.length())));
        }
        return res.toString();
    }
}