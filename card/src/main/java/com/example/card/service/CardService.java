package com.example.card.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;

@Service
public class CardService {
    final static String accountServer = "http://34.64.132.4:50055";
    public String getUserIdFromUsername(String username, String token) throws IOException, InterruptedException {
        
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(accountServer + "/user/id?username=" + username))
                .header("Authorization", token)
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() != 200){
            return "-1";
        }
        return response.body().substring(12, 13);
    } 

    public boolean checkUser(long userId, String token) throws Exception{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(accountServer + "/user/exists?userId=" +userId))
                .header("Authorization", token)
                .headers("Accept", "application/json")
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() != 200){
            return false;
        }
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = mapper.readValue(response.body(), Map.class);
        System.out.println(map);
        return Boolean.parseBoolean(map.get("message"));
    }
    public Map<String,Object> getUserInfo(long userId, String token) throws Exception{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(accountServer + "/user/info/id/" + userId))
                .header("Authorization", token)
                .headers("Accept", "application/json")
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() != 200){
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(response.body(), Map.class);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> body = objectMapper.convertValue(map.get("body"), Map.class);
        System.out.println(body.toString());
        return body;
    }
}
