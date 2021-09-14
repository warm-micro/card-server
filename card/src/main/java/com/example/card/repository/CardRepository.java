package com.example.card.repository;

import java.util.List;
import java.util.Optional;

import com.example.card.model.Card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>{  
    Optional<Card> findById(long id);
    List<Card> findBySprintId(long sprintId);
    boolean existsById(long id);
}
