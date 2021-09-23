package com.example.card.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonTagOrderResponse{
    private long id;
    private List<Long> tagOrder;
    private PTag personTag;
    private Long sprintId; 
    
   public PersonTagOrderResponse(PTagOrder pTagOrder) {
       this.id = pTagOrder.getId();
       this.personTag = pTagOrder.getPersonTag();
       this.sprintId = pTagOrder.getSprintId();
       List<String> tmp  = Arrays.asList(pTagOrder.getTagOrder().split(","));
       List<Long> tagOrder = new ArrayList<>();
       for(String t : tmp){
           try {
               tagOrder.add(Long.parseLong(t));
           } catch (Exception e) {
           }
       }
       this.tagOrder = tagOrder;
   }
}
