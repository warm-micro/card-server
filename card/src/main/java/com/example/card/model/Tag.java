package com.example.card.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private int order;
    private String color;
    private int cardOrder;

    public Tag() {}

    public Tag(String name, String color){
        this.name = name; 
        this.color = color;
    }
}
