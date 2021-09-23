package com.example.card.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardRequest implements Serializable{
    private static final long serialVersionUID = 5926468583005150707L;
    private String title;
    private long sprintId;
    private String content;
    private boolean isCard;
    private Progress progress;

    public boolean getIsCard(){
        return isCard;
    }
}
