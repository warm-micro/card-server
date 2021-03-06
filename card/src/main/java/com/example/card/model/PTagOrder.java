package com.example.card.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PTagOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String tagOrder;
    @OneToOne(targetEntity = PTag.class)
    private PTag personTag;
    private Long sprintId;

    public PTagOrder(PTag pTag, Long sprintId){
        this.personTag = pTag;
        this.sprintId = sprintId;
    }
}