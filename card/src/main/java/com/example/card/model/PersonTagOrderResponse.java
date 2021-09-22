package com.example.card.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
       this.tagOrder = Arrays.asList(pTagOrder.getTagOrder().split(",")).stream()
                            .map(s -> Long.parseLong(s))
                            .collect(Collectors.toList());
   }
}
