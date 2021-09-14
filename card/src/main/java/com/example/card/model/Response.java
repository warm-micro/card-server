package com.example.card.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Response implements Serializable {
    private static final long serialVersionUID = 39029340830275L;
    private final String message;
    private final Object body;
}
