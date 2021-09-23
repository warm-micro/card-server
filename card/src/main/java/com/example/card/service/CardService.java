package com.example.card.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CardService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    final static String accountServer1 = "http://34.64.132.4:50055";
    final static String accountServer2 = "http://3.38.108.235:50055";
    private String accountServer = accountServer1;

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
        HttpClient client = HttpClient.newBuilder()
            .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(accountServer + "/user/info/id/" + userId))
                .header("Authorization", token)
                .headers("Accept", "application/json")
                .timeout(Duration.ofSeconds(1))
                .build();
        HttpResponse<String> response;
        // response = client.send(request,
        //     HttpResponse.BodyHandlers.ofString());    
        // if(response.statusCode() != 200){
        //     return null;
        // }
        while(true){
            try {
                response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());    
                if(response.statusCode() != 200){
                    return null;
                }
                break;
            } catch (HttpTimeoutException e) { // Timeout Exception 시 다른 서비스로 요청
                logger.info("timeout");
                if(accountServer == accountServer1){
                    accountServer = accountServer2;
                } else {
                    accountServer = accountServer1;
                }
            }
        }
        
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(response.body(), Map.class);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> body = objectMapper.convertValue(map.get("body"), Map.class);
        return body;
    }
}
