package com.example.card.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CardResponse{
    private long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private long sprintId;
    private long authorId;
    private boolean isCard;
    private Progress progress;
    private int progressOrder;
    private Set<Object> pTags;
    private Set<Tag> hTags;

    public CardResponse(Card card){
        this.id = card.getId();
        this.title = card.getTitle();
        this.content = card.getContent();
        this.createdAt = card.getCreatedAt();
        this.sprintId = card.getSprintId();
        this.authorId = card.getAuthorId();
        this.isCard = card.getIsCard();
        this.progress = card.getProgress();
        this.progressOrder = card.getProgressOrder();
        this.hTags = card.getHTags();
        this.pTags = new HashSet<>();
    }
    public boolean getIsCard(){
        return isCard;
    }
    public void setIsCard(boolean isCard){
        this.isCard = isCard;
    }
}
