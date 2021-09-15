package com.example.card.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PersonTagResponse {
    private long userId;
    private String username;
    private String nickname;
    private String email;
    private String phoneNumber;
    private String color;
}
