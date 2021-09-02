package com.example.card.repository;

import java.util.Optional;

import com.example.card.model.PersonTag;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonTagRepository extends JpaRepository<PersonTag, Long>{
    Optional<PersonTag> findById(long id);
}
