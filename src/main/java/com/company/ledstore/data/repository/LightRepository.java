package com.company.ledstore.data.repository;

import com.company.ledstore.data.entity.Light;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LightRepository extends CrudRepository<Light, Long> {
}
