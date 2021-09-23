package com.example.card.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
// 챗/카드 데이터 정의
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String content;
    private long sprintId;
    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private long authorId;
    private boolean isCard;
    private Progress progress;
    private int progressOrder;
    @ManyToMany(targetEntity = PTag.class)
    private Set<PTag> pTags;
    @ManyToMany(targetEntity = Tag.class)
    private Set<Tag> hTags;

    public Card(String title, long sprintId, long authroId, boolean isCard, Progress progress){
        this.title = title;
        this.sprintId = sprintId;
        this.authorId = authroId;
        this.isCard = isCard;
        this.progress = progress;
    }

    public Card(CardRequest cardRequest, Long authorId){
        this.title = cardRequest.getTitle();
        this.content = cardRequest.getContent();
        this.sprintId = cardRequest.getSprintId();
        this.authorId = authorId;
        this.isCard = cardRequest.getIsCard();
        this.progress = cardRequest.getProgress();
    }
    public boolean getIsCard(){
        return isCard;
    }
    public void setIsCard(boolean isCard){
        this.isCard = isCard;
    }
}

