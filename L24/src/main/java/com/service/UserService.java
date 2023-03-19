package com.service;

import com.entity.Message;
import com.entity.User;
import com.repository.MessageRepository;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    public boolean addUser(User user) {
        try {
            Optional<User> optionalUser = userRepository.getUserByUsername(user.getUsername());
            if (!optionalUser.isEmpty()) {
                throw new Exception();
            } else {
                userRepository.save(user);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<User> findAll() {
        return userRepository.getAll();
    }

    public User getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    public Optional<User> getUserByUsernameAndPassword(String username, String password) {
        Optional<User> optionalUser = userRepository.getUserByUsernameAndPassword(username, password);
        return optionalUser;
    }

    public Optional<User> getByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    public void sendMessage(Long senderId, Long receiverId, String messageText) {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new RuntimeException("Receiver not found"));

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setMessageText(messageText);
        message.setSentDate(new Date());

        messageRepository.save(message);
    }
}
