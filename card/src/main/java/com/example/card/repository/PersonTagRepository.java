package com.example.card.repository;

import java.util.Optional;

import com.example.card.model.PTag;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonTagRepository extends JpaRepository<PTag, Long>{
    Optional<PTag> findById(long id);
    Optional<PTag> findByPersonId(long personId);
    boolean existsByPersonId(long personid);
}
