package com.example.demo.repositories;

import com.example.demo.entities.HelloMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HelloMessageRepository extends JpaRepository<HelloMessage,Long> {

    public List<HelloMessage> findAllByOrderByIdAsc();
}
