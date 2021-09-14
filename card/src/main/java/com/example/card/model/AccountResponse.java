package com.example.card.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AccountResponse implements Serializable{
    private static final long serialVersionUID = -3434000231234L;
    private final String message;
}
