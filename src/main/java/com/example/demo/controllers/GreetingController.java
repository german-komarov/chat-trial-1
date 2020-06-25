package com.example.demo.controllers;

import com.example.demo.dto.Greeting;
import com.example.demo.entities.HelloMessage;
import com.example.demo.repositories.HelloMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {

    @Autowired
    private HelloMessageRepository helloMessageRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @MessageMapping("/hello")
    public void greeting(HelloMessage message) throws Exception {
        helloMessageRepository.save(message);
        simpMessagingTemplate.convertAndSendToUser(message.getSender(),"/topic/greetings",new Greeting("<b>"+message.getSender()+"</b> : "+message.getMessage()));
        simpMessagingTemplate.convertAndSendToUser(message.getReceiver(),"/topic/greetings",new Greeting("<b>"+message.getSender()+"</b> : "+message.getMessage()));
    }

}