package com.example.demo.controllers;

import com.example.demo.entities.HelloMessage;
import com.example.demo.entities.Users;
import com.example.demo.repositories.HelloMessageRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.HelloMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainController {
    @Autowired
    private HelloMessageRepository helloMessageRepository;
    @Autowired
    private HelloMessageService helloMessageService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/chat")
    public String contacts(Model model)
    {
        model.addAttribute("contacts",userRepository.findAll());
        return "contacts";
    }

    @GetMapping("/chat/{receiver}")
    public String chatRoom(@AuthenticationPrincipal Users users, @PathVariable("receiver") String receiver,Model model) {
        HelloMessage helloMessage=new HelloMessage();
        helloMessage.setSender(users.getUsername());
        helloMessage.setReceiver(receiver);
        model.addAttribute("receiver",receiver);
        model.addAttribute("sender",users.getUsername());
        model.addAttribute("hellomessage",helloMessage);
        model.addAttribute("allMessages",helloMessageService.getAllMessages(users.getUsername(),receiver));
        return "message2";
    }





}