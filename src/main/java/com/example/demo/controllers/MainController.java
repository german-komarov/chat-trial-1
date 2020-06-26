package com.example.demo.controllers;

import com.example.demo.entities.ChatRoom;
import com.example.demo.entities.HelloMessage;
import com.example.demo.entities.Users;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.ChatRoomService;
import com.example.demo.services.HelloMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private HelloMessageService helloMessageService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/chat")
    public String mainPageOfTheChat(@AuthenticationPrincipal Users users, Model model)
    {
        model.addAttribute("username",users.getUsername());
        model.addAttribute("contacts",userRepository.findAll());
        return "contacts";
    }

    @GetMapping("/chat/{receiver}")
    public String pageWithExactlyUserChat(@AuthenticationPrincipal Users users, @PathVariable("receiver") String receiver,Model model) {

        List<ChatRoom> chatRoom = chatRoomService.getChatRoom(users.getUsername(),receiver);
        HelloMessage helloMessage=new HelloMessage();
        helloMessage.setSender(users.getUsername());
        helloMessage.setReceiver(receiver);
        if(!chatRoom.isEmpty())
        {
            helloMessage.setRoomId(chatRoom.get(0).getRoomId());
        }
        else
        {
            chatRoomService.saveChat(users.getUsername(),receiver);
            helloMessage.setRoomId(chatRoomService.getChatRoom(users.getUsername(),receiver).get(0).getRoomId());

        }
        model.addAttribute("hellomessage",helloMessage);
        model.addAttribute("allMessages",helloMessageService.getAllMessages(users.getUsername(),receiver));
        return "message";
    }





}