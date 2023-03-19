package com.service;

import com.entity.Friend;
import com.entity.User;
import com.repository.FriendRepository;
import com.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class FriendService {
    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    public void addToFriends(User firstUser, User secondUser) {
        Optional<Friend> optionalFriend = friendRepository.getFriendByFirstUserAndSecondUser(firstUser, secondUser);
        if (optionalFriend.isPresent()) {
            return;
        }

        Friend friends = new Friend();
        friends.setFirstUser(firstUser);
        friends.setSecondUser(secondUser);
        friendRepository.save(friends);

        Friend friendsSecond = new Friend();
        friendsSecond.setFirstUser(secondUser);
        friendsSecond.setSecondUser(firstUser);
        friendRepository.save(friendsSecond);
    }

    public Optional<Friend> getFriendByFirstAndSecondUser (User firstUser, User secondUser) {
        Optional<Friend> optionalFriend = friendRepository.getFriendByFirstUserAndSecondUser(firstUser, secondUser);
        return optionalFriend;
    }

    public ArrayList<User> getIdsOfFriends (User user) {
        return friendRepository.getAllFriendsUserIds(user);
    }

    public void deleteFriend (Long firstUserId, Long secondUserId) {
        User firstUser = userService.getUserById(firstUserId);
        User secondUser = userService.getUserById(secondUserId);

        friendRepository.delete(friendRepository.getFriendByFirstUserAndSecondUser(firstUser, secondUser).get());
        friendRepository.delete(friendRepository.getFriendByFirstUserAndSecondUser(secondUser, firstUser).get());

        messageService.deleteAllMessagesBetweenTwoUsers(firstUserId, secondUserId);
    }
}
