package com.message.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<com.message.app.domain.UserDomain, String> {
    com.message.app.domain.UserDomain findByEmail(String email);
    boolean existsByEmail(String email);
}
