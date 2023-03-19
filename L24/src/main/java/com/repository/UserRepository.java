package com.repository;

import com.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getUserByUsername(String username);

    User getUserById(Long id);

    @Query("select u from User u")
    ArrayList<User> getAll();

    Optional<User> getUserByUsernameAndPassword(String username, String password);
    
}
