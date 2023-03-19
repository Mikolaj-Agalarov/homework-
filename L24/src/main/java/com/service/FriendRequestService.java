package com.service;

import com.entity.FriendRequest;
import com.entity.User;
import com.repository.FriendRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class FriendRequestService {
    @Autowired
    private FriendRequestRepository friendRequestRepository;
    @Autowired
    private FriendService friendService;

    public void sendRequestToAddToFriends(User sender, User receiver) {
        if (friendService.getFriendByFirstAndSecondUser(sender, receiver).isPresent()) {
            return;
        }
        if (friendRequestRepository.findFriendRequestByFromUser_IdAndToUser_Id(sender.getId(),
                receiver.getId()).isPresent()) {
            return;
        }
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setFromUser(sender);
        friendRequest.setToUser(receiver);
        friendRequest.setStatus("awaiting for accepting");
        friendRequestRepository.save(friendRequest);
    }

    public ArrayList<FriendRequest> findAllRequestsFromUser (User sender) {
        ArrayList<FriendRequest> allRequests = friendRequestRepository.findAllByFromUser(sender);
        return allRequests;
    }

    public ArrayList<FriendRequest> findAllRequestsToUser (User sender) {
        ArrayList<FriendRequest> allRequests = friendRequestRepository.findAllByToUser(sender);
        return allRequests;
    }

    public Optional<FriendRequest> findFriendRequestByFromUserIdAndToUserId (Long fromUser, Long toUser) {
        return friendRequestRepository.findFriendRequestByFromUser_IdAndToUser_Id(fromUser, toUser);
    }

    public void acceptFriendRequest (Long id) {
        FriendRequest requestToAccept = friendRequestRepository.findFriendRequestById(id);
        friendService.addToFriends(requestToAccept.getToUser(), requestToAccept.getFromUser());
        friendRequestRepository.delete(requestToAccept);
    }

    public void declineFriendRequest (Long id) {
        FriendRequest requestToDecline = friendRequestRepository.findFriendRequestById(id);
        friendRequestRepository.delete(requestToDecline);
    }
}
