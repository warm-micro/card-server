package com.example.card.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.card.config.JwtUtils;
import com.example.card.model.Card;
import com.example.card.model.CardRequest;
import com.example.card.model.CardResponse;
import com.example.card.model.Response;
import com.example.card.repository.CardRepository;
import com.example.card.repository.PersonTagRepository;
import com.example.card.repository.TagRepository;
import com.example.card.service.CardService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/card")
public class CardController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private PersonTagRepository personTagRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private CardService cardService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> createCard(@RequestHeader Map<String, String> headers, @RequestBody CardRequest cardRequest) throws Exception{
        String tokenString = headers.get("authorization");
        String token = tokenString.substring(7);
        String username = jwtUtils.getUsernameFromToken(token);
        String userId = cardService.getUserIdFromUsername(username, tokenString);
        if (userId == "-1"){
            return ResponseEntity.badRequest().body(new CardResponse("wrong username", new Card()));
        }
        long authorId = Integer.parseInt(userId);
        Card card = new Card(cardRequest.getTitle(), cardRequest.getSprintId(), authorId, cardRequest.getIsCard(), cardRequest.getProgress());
        cardRepository.save(card);
        return ResponseEntity.ok().body(new Response("card is created", card));
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> listCardBySpringId(long sprintId){
        List<Card> cards = cardRepository.findBySprintId(sprintId);
        return ResponseEntity.ok().body(new Response("list card", cards));
    }
    
    @RequestMapping(value = "/{cardId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCard(@PathVariable Long cardId){
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        if(!cardOptional.isPresent()){
            return ResponseEntity.badRequest().body(new Response("card id is wrong", null));
        }
        Card card = cardOptional.get();
        cardRepository.delete(card);
        return ResponseEntity.ok().body(new Response("card is deleted", null));
    }
    @RequestMapping(value="/ping", method=RequestMethod.POST)
    public ResponseEntity<?> requestMethodName(@RequestHeader Map<String, String> headers) {
        logger.info(headers.get("authorization"));
        return ResponseEntity.ok().body(new String("pong"));
    }
}
