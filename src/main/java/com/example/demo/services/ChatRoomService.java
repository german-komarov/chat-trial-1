package com.example.demo.services;


import com.example.demo.entities.ChatRoom;
import com.example.demo.repositories.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private EntityManager entityManager;


    public ChatRoom getOne(Long id)
    {
        if(chatRoomRepository.findById(id).isPresent())
        {
            return chatRoomRepository.findById(id).get();
        }
        else
        {
            return null;
        }
    }

    public void deleteChat(Long id)
    {
        chatRoomRepository.deleteById(id);
    }

    public void saveChat(String sender,String receiver)
    {
        ChatRoom chatRoom=new ChatRoom();

        if(sender.compareTo(receiver)>=0)
        {

            //In Alphabetic order
            chatRoom.setFirstParticipant(receiver);
            chatRoom.setSecondParticipant(sender);
        }

        else
        {
            //In Alphabetic order
            chatRoom.setFirstParticipant(sender);
            chatRoom.setSecondParticipant(receiver);
        }

        chatRoom.setRoomId(UUID.randomUUID().toString());
        chatRoomRepository.save(chatRoom);

    }



    public List<ChatRoom> getChatRoom(String username,String receiver)
    {
        return entityManager.createQuery(
                "select room from ChatRoom room where " +
                        "room.firstParticipant like :paramFirstParticipant " +
                        "and " +
                        "room.secondParticipant like :paramSecondParticipant " +
                "or " +
                        "room.firstParticipant like :paramSecondParticipant " +
                        "and " +
                        "room.secondParticipant like :paramFirstParticipant", ChatRoom.class)
                .setParameter("paramFirstParticipant",username)
                .setParameter("paramSecondParticipant",receiver)
                .getResultList();
    }

    public List<ChatRoom> getAllChatRoomsForThisUser(String username)
    {
        return entityManager.createQuery(
                "select room from ChatRoom room where " +
                        "room.firstParticipant like :paramUsername " +
                        "or " +
                        "room.secondParticipant like :paramUsername",ChatRoom.class)
                .setParameter("paramUsername",username)
                .getResultList();
    }


}
