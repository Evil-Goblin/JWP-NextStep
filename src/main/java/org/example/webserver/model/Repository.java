package org.example.webserver.model;

import java.util.List;
import java.util.Optional;

public interface Repository<T, U> {
    Optional<T> findById(U id);

    T save(T t);

    T update(T t);

    T delete(T t);

    List<T> findAll();
}
