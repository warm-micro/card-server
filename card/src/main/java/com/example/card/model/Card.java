package com.example.card.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private long sprintId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    private long authorId;
    private boolean isCard;
    private Progress progress;
    private int progressOrder;

    public Card() {}

    public Card(String title, long sprintId, long authroId, boolean isCard, Progress progress){
        this.title = title;
        this.sprintId = sprintId;
        this.authorId = authroId;
        this.isCard = isCard;
        this.progress = progress;
    }
}

