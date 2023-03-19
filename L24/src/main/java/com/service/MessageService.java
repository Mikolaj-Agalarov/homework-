package com.service;

import com.entity.Message;
import com.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public List<Message> findAllMessagesBySenderAndReceiver (Long senderId, Long receiverId) {
        List<Message> messages = messageRepository.findAllMessagesBySenderAndReceiver(senderId, receiverId);
        messageRepository.findAllMessagesBySenderAndReceiver(receiverId, senderId)
                .stream().forEach(message -> messages.add(message));

        List<Message> sortedMessages = messages.stream()
                .sorted(Comparator.comparing(Message::getSentDate).reversed())
                .collect(Collectors.toList());

        return sortedMessages;
    }

    public void deleteAllMessagesBetweenTwoUsers (Long senderId, Long receiverId) {
        List<Message> messages = findAllMessagesBySenderAndReceiver(senderId, receiverId);
        messages.stream().forEach(message -> messageRepository.delete(message));
    }

}
