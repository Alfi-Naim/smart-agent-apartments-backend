package com.alfons.smartagent.repo;

import com.alfons.smartagent.model.SearchRequest;
import org.springframework.data.repository.CrudRepository;

public interface SearchRepository extends CrudRepository<SearchRequest,Long> {}