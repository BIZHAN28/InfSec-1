package com.bizhanov.secureapi.repository;

import com.bizhanov.secureapi.model.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    
    @Query("SELECT * FROM users WHERE username = :username")
    Optional<User> findByUsername(@Param("username") String username);
}

