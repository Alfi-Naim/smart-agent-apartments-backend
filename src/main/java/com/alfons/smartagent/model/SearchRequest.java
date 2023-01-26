package com.alfons.smartagent.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="search")
public class SearchRequest implements Serializable {

    @Id
    @Column(nullable = false, updatable = false)
    private String searchId;

    private String type;

    private String city;

    private String minimumPrice;

    private String maximumPrice;

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMaximumPrice() {
        return maximumPrice;
    }

    public void setMaximumPrice(String maximumPrice) {
        this.maximumPrice = maximumPrice;
    }

    public String getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(String minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public static final class SearchRequestBuilder {
        private String searchId;
        private String type;
        private String city;
        private String minimumPrice;
        private String maximumPrice;

        private SearchRequestBuilder() {
        }

        public static SearchRequestBuilder aSearchRequest() {
            return new SearchRequestBuilder();
        }

        public SearchRequestBuilder searchId(String searchId) {
            this.searchId = searchId;
            return this;
        }

        public SearchRequestBuilder type(String type) {
            this.type = type;
            return this;
        }

        public SearchRequestBuilder city(String city) {
            this.city = city;
            return this;
        }

        public SearchRequestBuilder minimumPrice(String minimumPrice) {
            this.minimumPrice = minimumPrice;
            return this;
        }

        public SearchRequestBuilder maximumPrice(String maximumPrice) {
            this.maximumPrice = maximumPrice;
            return this;
        }

        public SearchRequest build() {
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.setSearchId(searchId);
            searchRequest.setType(type);
            searchRequest.setCity(city);
            searchRequest.setMinimumPrice(minimumPrice);
            searchRequest.setMaximumPrice(maximumPrice);
            return searchRequest;
        }
    }
}
