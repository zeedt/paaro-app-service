package com.zeed.isms.controller;

import com.zeed.isms.rest.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/")
public class UserController {

    @Value("${oauth.authentication.url:http://127.0.0.1:8011/oauth/token}")
    public String oauthUrl;

    @Autowired
    private RestClient restClient;

    @RequestMapping(value = "home", method = RequestMethod.GET)
    public String home(Principal principal){
        return "home";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(Principal principal){
        return "login";
    }


    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String loginUser(Principal principal, @RequestParam Map<String,String> loginDetails ) {
        String base64encodedString = Base64.getEncoder().encodeToString("isms-service:secret".getBytes());
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Basic " + base64encodedString);
        MultiValueMap<String, Object> request = new LinkedMultiValueMap();
        request.add("username", loginDetails.get("username"));
        request.add("password", loginDetails.get("password"));
        request.add("grant_type", "password");
        String url = oauthUrl + "?grant_type=password&username=" + loginDetails.get("username") + "&password=" + loginDetails.get("password");
        ResponseEntity<OAuth2AccessToken> oAuth2AccessTokenResponseEntity =
                restClient.apiPostWithHttpEntity(url, OAuth2AccessToken.class, request, headers);
        return "redirect:/home";
    }


    @RequestMapping(value = "wwwww", method = RequestMethod.POST)
    public String logins(Principal principal){
        return "login";
    }
    @RequestMapping(value = "wwwww", method = RequestMethod.GET)
    public String loginss(Principal principal){
        return "login";
    }
    @ResponseBody
    @RequestMapping(value = "getHash", method = RequestMethod.GET)
    public HashMap<String,String> getDD(Principal principal){
        HashMap<String,String> hashMap = new HashMap<String,String>();
        hashMap.put("Answer","answer");
        return hashMap;
    }



}
