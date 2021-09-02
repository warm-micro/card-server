package com.example.card.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CardResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String message;
    private final Card card;
}
