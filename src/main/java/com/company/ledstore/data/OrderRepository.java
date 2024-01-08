package com.company.ledstore.data;

import com.company.ledstore.LightOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<LightOrder, Long> {
}
