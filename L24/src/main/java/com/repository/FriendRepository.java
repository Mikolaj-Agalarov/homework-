package com.repository;

import com.entity.Friend;
import com.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    Optional<Friend> getFriendByFirstUserAndSecondUser (User firstUser, User secondUser);

    ArrayList<Friend> getAllByFirstUser (User user);

    @Query("SELECT f.secondUser FROM Friend f WHERE f.firstUser = :user")
    ArrayList<User> getAllFriendsUserIds(@Param("user") User user);
}
