package com.alfons.smartagent.model;

import static com.alfons.smartagent.model.Apartment.ApartmentBuilder.anApartment;

public class FeedItem {

    String id;
    String searchId;
    String street;
    String line_1;
    String line_2;
    String price;
    String link;
    int square_meters;

    public FeedItem(String id, String street, String line_1, String line_2, String price, int square_meters) {
        this.id = id;
        this.street = street;
        this.line_1 = line_1;
        this.line_2 = line_2;
        this.price = price;
        this.square_meters = square_meters;
        this.link = "https://www.yad2.co.il/item/" + this.id;

        if (street != null) this.street = street.replace("\"", "");
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    public String getLine_1() {
        return line_1;
    }

    public String getLine_2() {
        return line_2;
    }

    public int getSquare_meters() {
        return square_meters;
    }

    public String getPrice() {
        return price;
    }

    public String getStreet() {
        return street;
    }

    public String getId() {
        return id;
    }

    public Apartment toApartment() {
        return anApartment().postId(id).floor(line_2).rooms(line_1).squareMeters(square_meters + "").street(street).price(price).link(link).searchId(searchId).build();
    }

    @Override
    public String toString() {
        return "\n{" + "\n" +
                "\"id\":\"" + id + "\"," + "\n" +
                "\"street\":\"" + street + "\"," + "\n" +
                "\"rooms\":\"" + line_1 + "\"," + "\n" +
                "\"floor\":\"" + line_2 + "\"," + "\n" +
                "\"squareMeters\":\"" + square_meters + "\"," + "\n" +
                "\"price\":\"" + price + "\"," + "\n" +
                "\"link\":\"" + link + "\"" + "\n" +
                "}";
    }
}