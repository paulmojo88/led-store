package com.company.ledstore.service;

import java.util.List;

public interface AbstractService<T, K> {
    T create(T entity);

    T getById(K id);

    T update(T entity);

    void delete(K id);

    List<T> findAll();

}
