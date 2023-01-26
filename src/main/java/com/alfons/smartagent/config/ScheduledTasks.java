package com.alfons.smartagent.config;

import com.alfons.smartagent.model.ApartmentsFeed;
import com.alfons.smartagent.model.FeedItem;
import com.alfons.smartagent.model.SearchRequest;
import com.alfons.smartagent.repo.ApartmentService;
import com.alfons.smartagent.repo.SearchService;
import com.alfons.smartagent.service.Yad2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ScheduledTasks {

	@Autowired
	ApartmentService apartmentService;

	@Autowired
	SearchService searchService;

	@Autowired
	Yad2Service yad2Service;

	@Scheduled(fixedRate = 5000)
	public void searchApartments() throws IOException {
		Iterable<SearchRequest> all = searchService.all();
		for (SearchRequest searchRequest : all) {
			ApartmentsFeed feed = yad2Service.searchApartments(searchRequest.getCity(), searchRequest.getType(), searchRequest.getMinimumPrice(), searchRequest.getMaximumPrice());
			List<FeedItem> feedItems = feed.getFeed_items();
			for (FeedItem feedItem : feedItems) {
				if(feedItem.getId() != null) {
					if(feedItem.getStreet() == null) feedItem.setStreet(searchRequest.getCity());
					feedItem.setSearchId(searchRequest.getSearchId());
					feedItem.setId(feedItem.getId() + searchRequest.getSearchId());
					apartmentService.save(feedItem.toApartment());
				}
			}
		}
	}
}