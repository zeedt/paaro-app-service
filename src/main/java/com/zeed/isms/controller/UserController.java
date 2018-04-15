package com.zeed.isms.controller;

import com.zeed.isms.services.OauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private OauthService oauthService;

    @RequestMapping(value = "home", method = RequestMethod.GET)
    public String home(Principal principal){
        return "home";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(Principal principal){
        return "login";
    }


    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String loginUser(Principal principal, @RequestParam Map<String,String> loginDetails ) throws Exception {
        ResponseEntity<OAuth2AccessToken> oAuth2AccessToken = oauthService.getACcessToken(loginDetails);
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
