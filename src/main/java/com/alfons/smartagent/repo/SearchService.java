package com.alfons.smartagent.repo;

import com.alfons.smartagent.model.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SearchService {

    @Autowired
    SearchRepository repository;

    public Iterable<SearchRequest> all() {
        return repository.findAll();
    }

    public Optional<SearchRequest> findById(Long id) {
        return repository.findById(id);
    }

    public SearchRequest save(SearchRequest searchRequest) {
        return repository.save(searchRequest);
    }

    public void delete(SearchRequest apartment) {
        repository.delete(apartment);
    }
}