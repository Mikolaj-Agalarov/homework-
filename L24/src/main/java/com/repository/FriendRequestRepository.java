package com.repository;

import com.entity.FriendRequest;
import com.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    Optional<FriendRequest> findFriendRequestByFromUser_IdAndToUser_Id (Long fromUserId, Long toUserId);

    ArrayList<FriendRequest> findAllByFromUser (User user);

    ArrayList<FriendRequest> findAllByToUser (User user);

    FriendRequest findFriendRequestById (Long id);
}
