package com.example.card.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.example.card.config.JwtUtils;
import com.example.card.model.Card;
import com.example.card.model.CardRequest;
import com.example.card.model.CardResponse;
import com.example.card.model.PTag;
import com.example.card.model.PTagOrder;
import com.example.card.model.PersonTagResponse;
import com.example.card.model.Progress;
import com.example.card.model.Response;
import com.example.card.model.Tag;
import com.example.card.repository.CardRepository;
import com.example.card.repository.PersonTagOrderRepository;
import com.example.card.repository.PersonTagRepository;
import com.example.card.repository.TagRepository;
import com.example.card.service.CardService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    private PersonTagOrderRepository personTagOrderRepository;

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
            return ResponseEntity.badRequest().body(new Response("wrong username", null));
        }
        long authorId = Integer.parseInt(userId);
        Card card = new Card(cardRequest.getTitle(), cardRequest.getSprintId(), authorId, cardRequest.getIsCard(), cardRequest.getProgress());
        cardRepository.save(card);
        return ResponseEntity.ok().body(new Response("card is created", card));
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> listCardBySprintId(@RequestHeader Map<String, String> headers, long sprintId) throws Exception{
        String tokenString = headers.get("authorization");
        List<Card> cards = cardRepository.findBySprintId(sprintId);
        List<CardResponse> cardResponses = new ArrayList<>();
        for(Card card : cards){
            CardResponse cardResponse = new CardResponse(card);
            for(PTag pTag : card.getPTags()){
                Map<String, Object> userInfo = cardService.getUserInfo(pTag.getPersonId(), tokenString);
                if(userInfo != null){
                    logger.info("test");
                    PersonTagResponse pTagResponse = new PersonTagResponse(
                        Long.parseLong(userInfo.get("id").toString()),
                        String.valueOf(userInfo.get("username")),
                        String.valueOf(userInfo.get("nickname")),
                        String.valueOf(userInfo.get("email")),
                        String.valueOf(userInfo.get("phoneNumber")),
                        pTag.getColor()
                    );
                    logger.info(pTagResponse.toString());
                    if(cardResponse.getPTags().isEmpty()){
                        logger.info("empty");
                        cardResponse.setPTags(new HashSet<Object>());
                    } 
                    cardResponse.getPTags().add(pTagResponse);
                }
            }
            cardResponses.add(cardResponse);
        }
        return ResponseEntity.ok().body(new Response("list card", cardResponses));
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

    @RequestMapping(value = "/exists/{cardId}", method = RequestMethod.GET)
    public ResponseEntity<?> existsCard(@PathVariable Long cardId){
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        return ResponseEntity.ok().body(new Response("card exists", cardOptional.isPresent()));
    }

    @PostMapping(value="/{cardId}/ptag")
    public ResponseEntity<?> postMethodName(@RequestHeader Map<String, String> headers, @PathVariable long cardId, @RequestBody PTag pTagRequest) throws Exception {
        long personId = pTagRequest.getPersonId();
        if (!cardService.checkUser(personId, headers.get("authorization"))) {
            return ResponseEntity.badRequest().body(new Response("wrong person id", null));
        }   
        Optional<PTag> oPtag = personTagRepository.findByPersonId(pTagRequest.getPersonId());
        PTag pTag;
        if (oPtag.isPresent()){
            pTag = oPtag.get();
        } else {
            personTagRepository.save(pTagRequest);
            pTag = pTagRequest;
        }
        logger.info(Long.toString(pTag.getId()));
        Optional<PTagOrder> oPTagOrder = personTagOrderRepository.findByPersonTag(pTag);
        logger.info("person tag2");
        PTagOrder personTagOrder = oPTagOrder.isPresent() ? oPTagOrder.get() : new PTagOrder(pTag);
        logger.info("person tag3");
        Optional<Card> oCard = cardRepository.findById(cardId); 
        if (!oCard.isPresent()){
            return ResponseEntity.badRequest().body(new Response("wrong card id", null));
        }
        Card card = oCard.get();
        if (card.getPTags().isEmpty()){
            Set<PTag> pTags = new HashSet<>();
            pTags.add(pTag);
            card.setPTags(pTags);
        } else{
            card.getPTags().add(pTag);
        }
        cardRepository.save(card);

        String order = personTagOrder.getTagOrder();
        if(order == null){
            order = Long.toString(card.getId());
        } else {
            order += "," + Long.toString(card.getId());
        }
        personTagOrder.setTagOrder(order);
        personTagOrder.setSprintId(card.getSprintId());
        personTagOrderRepository.save(personTagOrder);
        return ResponseEntity.ok().body(new Response("person tag created", card));
    }
    

    @PostMapping(value="/{cardId}/htag")
    public ResponseEntity<?> addTag(@RequestHeader Map<String, String> headers, @PathVariable long cardId, @RequestBody Tag tagRequest) {
        Optional<Tag> oTag = tagRepository.findByName(tagRequest.getName());
        Tag tag;
        if(oTag.isPresent()){
            tag = oTag.get();
        } else{
            tagRepository.save(tagRequest);
            tag = tagRequest;
        }
        Optional<Card> oCard = cardRepository.findById(cardId);
        if (!oCard.isPresent()){
            return ResponseEntity.badRequest().body(new Response("wrong card id", null));
        }
        Card card = oCard.get();
        if(card.getHTags().isEmpty()){
            Set<Tag> tags = new HashSet<>();
            tags.add(tag);
            card.setHTags(tags);
        } else {
            card.getHTags().add(tag);
        }
        cardRepository.save(card);
        return ResponseEntity.ok().body(new Response("person tag created", card));
    }
    
    @PutMapping(value="/flag/{cardId}")
    public ResponseEntity<?> changeToIsCard(@PathVariable Long cardId) {
        Optional<Card> oCard = cardRepository.findById(cardId);
        if(!oCard.isPresent()){
            return ResponseEntity.badRequest().body(new Response("wrong card id", null));
        }
        Card card = oCard.get();
        card.setCard(!card.getIsCard());
        cardRepository.save(card);
        return ResponseEntity.ok().body(new Response("card flage changed", card));
    }
    @RequestMapping(value="/ping", method=RequestMethod.POST)
    public ResponseEntity<?> requestMethodName(@RequestHeader Map<String, String> headers) {
        logger.info(headers.get("authorization"));
        return ResponseEntity.ok().body(new String("pong"));
    }
    @GetMapping(value="/search")
    public ResponseEntity<?> searchCards(@RequestParam(required = false) String title, @RequestParam(required = false)Long authorId, @RequestParam(required = false) String progress) {
        Map<String, List<Card>> mapResponse = new HashMap<>();
        List<Card> titleSearchResult = new ArrayList<Card>();
        List<Card> authorSearchResult = new ArrayList<Card>();
        List<Card> progressSearchResult = new ArrayList<Card>();
        try {
            Progress progressQuery = Progress.valueOf(progress);
            progressSearchResult.addAll(cardRepository.findByProgress(progressQuery));
        } catch (Exception e) {

        }
        mapResponse.put("progress", progressSearchResult);

        if(title  != null){
            titleSearchResult.addAll(cardRepository.findByTitleContaining(title));
        }
        mapResponse.put("title", titleSearchResult);
        if(authorId != null){
            authorSearchResult.addAll(cardRepository.findByAuthorId(authorId));
        }
        mapResponse.put("author", authorSearchResult);
        
        return ResponseEntity.ok().body(new Response("searsh result", mapResponse));
    }

    @GetMapping(value="/pTagOrder/{sprintId}")
    public ResponseEntity<?> getPTagOrders(@PathVariable Long sprintId) {
        List<PTagOrder> pTagOrders = personTagOrderRepository.findBySprintId(sprintId);
        return ResponseEntity.ok().body(new Response("order list", pTagOrders));
    }
    @PutMapping(value="/pTagOrder/{id}")
    public ResponseEntity<?> putPTagOrder(@PathVariable Long id, @RequestBody PTagOrder pTagOrderReq) {
        Optional<PTagOrder> oPTagOrder = personTagOrderRepository.findById(id);
        if(!oPTagOrder.isPresent()){
            return ResponseEntity.badRequest().body(new Response("wrong person tag order id", null));
        }
        PTagOrder pTagOrder = oPTagOrder.get();
        pTagOrder.setTagOrder(pTagOrder.getTagOrder());
        personTagOrderRepository.save(pTagOrder);
        return ResponseEntity.ok().body(new Response("order updated", pTagOrder));
    }
}
