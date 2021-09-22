package com.example.card.model;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter 
@NoArgsConstructor
public class OrderReqeust implements Serializable{
    private static final long serialVersionUID = 5926468124356927L;   
    private List<Long> tagOrders;
}
