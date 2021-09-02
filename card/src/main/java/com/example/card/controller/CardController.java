package com.example.card.controller;

import com.example.card.model.CardRequest;
import com.example.card.repository.CardRepository;
import com.example.card.repository.PersonTagRepository;
import com.example.card.repository.TagRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/card")
public class CardController {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private PersonTagRepository personTagRepository;

    @Autowired
    private TagRepository tagRepository;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> createCard(@RequestBody CardRequest cardRequest) throws Exception{
        System.out.println(cardRequest.getTitle());
        return ResponseEntity.ok().body(new String("test"));
    }
    @RequestMapping(value="/ping", method=RequestMethod.GET)
    public ResponseEntity<?> requestMethodName() {
        return ResponseEntity.ok().body(new String("pong"));
    }
    
}
