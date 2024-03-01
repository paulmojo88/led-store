package com.company.ledstore.data.repository;

import com.company.ledstore.data.entity.LightOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LightOrderRepository extends CrudRepository<LightOrder, Long> {
}
