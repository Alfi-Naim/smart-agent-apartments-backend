package com.alfons.smartagent.service;

import org.springframework.stereotype.Service;

@Service
public class Yad2Service {

    public String searchApartments(String keyword) {
        return "Searched for:" + keyword;
    }
}