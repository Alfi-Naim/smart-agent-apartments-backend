package com.alfons.smartagent.controller;

import com.alfons.smartagent.service.Yad2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/bot")
public class BotController {

    @Autowired
    Yad2Service yad2Service;

    @RequestMapping(value = "/yad2", method = RequestMethod.GET)
//    public ResponseEntity<?> getApartments() throws IOException {
    public ResponseEntity<?> getApartments(@RequestParam String city) throws IOException {
        //TODO from city name to city id
//        return new ResponseEntity<>(yad2Service.searchApartments(city, type, maximumPrice), HttpStatus.OK);
        return new ResponseEntity<>(yad2Service.searchApartments(city, "apartments", "5000"), HttpStatus.OK);
    }
}