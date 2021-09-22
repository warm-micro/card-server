package com.example.card.repository;

import java.util.List;
import java.util.Optional;

import com.example.card.model.PTag;
import com.example.card.model.PTagOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonTagOrderRepository extends JpaRepository<PTagOrder, Long>{
    Optional<PTagOrder> findById(Long id);
    Optional<PTagOrder> findByPersonTag(PTag pTag);
    List<PTagOrder> findBySprintId(Long sprintId);
}
