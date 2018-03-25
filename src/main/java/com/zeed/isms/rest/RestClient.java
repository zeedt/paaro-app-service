package com.zeed.isms.rest;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
public class RestClient {

    public <T> ResponseEntity<T> apiPostWithHttpEntity(String url, Class<T> claz, MultiValueMap<String,Object> requestData, HashMap<String,String> headers){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.entrySet().stream().forEach(headers1->{
            httpHeaders.set(headers1.getKey(),headers1.getValue());
        });
        HttpEntity httpEntity = new HttpEntity(requestData,httpHeaders);

        ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.POST,httpEntity,claz,new Object[0]);
        return responseEntity;
    }

    public <T> ResponseEntity<T> apiPostWithHttpEntityt(String url, HttpEntity httpEntity, Class<T> claz){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.POST,httpEntity,claz,new Object[0]);
        return responseEntity;
    }



}
