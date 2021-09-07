package com.example.card.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class LogRequestFilter extends OncePerRequestFilter{
    final static String LogServer = "http://13.124.188.6:50051/log";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        chain.doFilter(request, response);
        long executionTime = System.currentTimeMillis() - startTime;
        
        HttpPost httpPost = new HttpPost(LogServer);
        
        List<NameValuePair> requestParmeters = new ArrayList<>();
        requestParmeters.add(new BasicNameValuePair("api", request.getRequestURI()));
        requestParmeters.add(new BasicNameValuePair("status", String.valueOf(response.getStatus())));
        requestParmeters.add(new BasicNameValuePair("latency", String.valueOf(executionTime)));

        httpPost.setEntity(new UrlEncodedFormEntity(requestParmeters));

        try(CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
                logger.info(EntityUtils.toString(httpResponse.getEntity()));
            }
        
    }   
}
