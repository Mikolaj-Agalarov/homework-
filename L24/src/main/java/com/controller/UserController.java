package com.controller;

import jakarta.servlet.http.HttpSession;
import com.entity.Friend;
import com.entity.FriendRequest;
import com.entity.Message;
import com.entity.User;
import com.repository.FriendRepository;

import com.service.FriendRequestService;
import com.service.FriendService;
import com.service.MessageService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

import java.util.List;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private FriendRequestService friendRequestService;
    @Autowired
    private FriendRepository friendRepository;
    @Autowired
    private MessageService messageService;

    @Autowired
    private FriendService friendService;

    @GetMapping("users")
    public String showAllUsers(Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");

        ArrayList<Long> friendIds = (ArrayList<Long>) friendRepository.getAllFriendsUserIds(user)
                .stream().map(user1 -> user1.getId()).collect(Collectors.toList());

        ArrayList<Long> idsOfUsersWaitingForAccept = (ArrayList<Long>) friendRequestService.findAllRequestsToUser(user)
                .stream().map(friendRequest -> friendRequest.getFromUser().getId()).collect(Collectors.toList());

        ArrayList<Long> idsOfUsersThatRequestWasSentTo = (ArrayList<Long>) friendRequestService.findAllRequestsFromUser(user)
                        .stream().map(friendRequest -> friendRequest.getToUser().getId()).collect(Collectors.toList());


        model.addAttribute("users", userService.findAll());
        model.addAttribute("userFriendsIds", friendIds);
        model.addAttribute("usersIdsAwaitingForAcceptingRequest", idsOfUsersWaitingForAccept);

        model.addAttribute("idsOfUsersThatRequestWasSentTo", idsOfUsersThatRequestWasSentTo);

        return "users";
    }

    @PostMapping("add-friend")
    public String addFriend(@RequestParam(name = "senderId") Long senderId,
                            @RequestParam(name = "receiverId") Long receiverId) {
        User sender = userService.getUserById(senderId);
        User receiver = userService.getUserById(receiverId);

        friendRequestService.sendRequestToAddToFriends(sender, receiver);

        return "redirect:/users/users";
    }

    @GetMapping("outgoing-requests")
    public String showOutgoingRequests(Model model, HttpSession session) {
        ArrayList<FriendRequest> allRequestsFromUser = friendRequestService
                .findAllRequestsFromUser((User) session.getAttribute("user"));

        model.addAttribute("allRequestsFromUser", allRequestsFromUser);

        return "outgoing-requests";
    }

    @GetMapping("ingoing-requests")
    public String showInoingRequests(Model model, HttpSession session) {
        ArrayList<FriendRequest> allRequestsToUser = friendRequestService
                .findAllRequestsToUser((User) session.getAttribute("user"));
        model.addAttribute("allRequestsToUser", allRequestsToUser);
        return "ingoing-requests";
    }

    @GetMapping("friend-list")
    public String showFriends(Model model, HttpSession session) {
        ArrayList<Friend> allFriends = friendRepository
                .getAllByFirstUser((User) session.getAttribute("user"));
        model.addAttribute("allFriends", allFriends);
        return "friends";
    }

    @PostMapping("accept-friend")
    public String acceptRequest(@RequestParam("requestId") Long requestId, @RequestParam("senderId") Long senderId,
                                @RequestParam("receiverId") Long receiverId) {

        if(requestId != 0L) {
            friendRequestService.acceptFriendRequest(requestId);
            return "redirect:/users/friend-list";
        } else {
            Long friendRequestId = friendRequestService.
                    findFriendRequestByFromUserIdAndToUserId(senderId, receiverId).get().getId();
            friendRequestService.acceptFriendRequest(friendRequestId);
            return "redirect:/users/friend-list";
        }
    }

    @PostMapping("decline-friend")
    public String declineRequest(@RequestParam("requestId") Long requestId, @RequestParam("senderId") Long senderId,
                                 @RequestParam("receiverId") Long receiverId) {
        if(requestId != 0L) {
            friendRequestService.declineFriendRequest(requestId);
            return "redirect:/users/outgoing-requests";
        } else {
            Long friendRequestId = friendRequestService.
                    findFriendRequestByFromUserIdAndToUserId(senderId, receiverId).get().getId();
            friendRequestService.declineFriendRequest(friendRequestId);
            return "redirect:/users/users";
        }

    }

    @GetMapping("open-messenger")
    public String sendMessage(@RequestParam("senderId") Long senderId,
                              @RequestParam("receiverId") Long receiverId, Model model) {
        model.addAttribute("user", userService.getUserById(receiverId));

        List<Message> messages = messageService.findAllMessagesBySenderAndReceiver(senderId, receiverId);

        model.addAttribute("messages", messages);
        return "messenger";
    }

    @PostMapping("send-message")
    public String sendMessage(@RequestParam("senderId") Long senderId,
                              @RequestParam("receiverId") Long receiverId,
                              @RequestParam("messageText") String messageText,
                              RedirectAttributes redirectAttributes) {
        userService.sendMessage(senderId, receiverId, messageText);
        redirectAttributes.addAttribute("senderId", senderId);
        redirectAttributes.addAttribute("receiverId", receiverId);
        return "redirect:open-messenger";
    }

    @PostMapping("delete-friend")
    public String deleteMessage(@RequestParam("senderId") Long senderId,
                              @RequestParam("receiverId") Long receiverId) {
        friendService.deleteFriend(senderId, receiverId);
        return "redirect:/users/friend-list";
    }
}
