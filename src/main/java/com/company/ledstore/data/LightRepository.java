package com.company.ledstore.data;

import com.company.ledstore.Light;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LightRepository extends CrudRepository<Light, Long> {
}
