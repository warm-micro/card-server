package com.example.card.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PersonTag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int tagOrder;
    private long personId;
    private long cardId;

    public PersonTag(long personId, long cardId){
        this.personId = personId;
        this.cardId = cardId;
    }
}
