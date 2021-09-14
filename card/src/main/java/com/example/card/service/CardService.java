package com.example.card.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
}
