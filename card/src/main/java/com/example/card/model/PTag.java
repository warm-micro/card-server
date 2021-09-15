package com.example.card.model;

import javax.persistence.Column;
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
public class PTag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int tagOrder;
    @Column(unique = true)
    private long personId;
    private long cardOrder;
    private String color;

    public PTag(long personId, long cardOrder){
        this.personId = personId;
        this.cardOrder = cardOrder;
    }
}
