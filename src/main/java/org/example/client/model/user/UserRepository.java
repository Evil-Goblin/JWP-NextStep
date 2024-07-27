package org.example.client.model.user;

import org.example.webserver.model.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {

    Optional<User> findByUserId(String userId);
}
