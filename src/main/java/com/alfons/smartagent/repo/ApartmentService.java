package com.alfons.smartagent.repo;

import com.alfons.smartagent.model.Apartment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApartmentService {

    @Autowired
    ApartmentRepository repository;

    public Iterable<Apartment> all() {
        return repository.findAll();
    }

    public Optional<Apartment> findById(Long id) {
        return repository.findById(id);
    }

    public Apartment save(Apartment apartment) {
        return repository.save(apartment);
    }

    public void delete(Apartment apartment) {
        repository.delete(apartment);
    }
}