package com.alfons.smartagent.repo;

import com.alfons.smartagent.model.Apartment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ApartmentRepository extends CrudRepository<Apartment,Long> {

//    @Query("SELECT a FROM apartments a WHERE a.searchId = :searchId")
//    List<Apartment> findApartmentsBySearchId(@Param("searchId") String searchId);
}