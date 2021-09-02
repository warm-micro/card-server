package com.example.card.repository;

import java.util.Optional;

import com.example.card.model.Tag;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long>{
    Optional<Tag> findById(long id);
}
