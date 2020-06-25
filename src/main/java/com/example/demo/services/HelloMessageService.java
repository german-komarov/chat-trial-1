package com.example.demo.services;


import com.example.demo.entities.HelloMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class HelloMessageService {


    @Autowired
    private EntityManager entityManager;


    public List<HelloMessage> getAllMessages(String sender,String receiver)
    {
        return entityManager.createQuery("select message from HelloMessage message where (message.sender like :paramSender " +
                "and message.receiver like :paramReceiver)" +
                "or (message.sender like :paramReceiver and message.receiver like :paramSender)",HelloMessage.class)
                .setParameter("paramSender",sender)
                .setParameter("paramReceiver",receiver)
                .getResultList();
    }


}
