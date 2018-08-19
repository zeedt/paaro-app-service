package com.zeed.paaro.controller;

import com.zeed.usermanagement.apimodels.ManagedUserModelApi;
import com.zeed.usermanagement.models.Authority;
import com.zeed.usermanagement.repository.AuthorityRepository;
import com.zeed.usermanagement.request.AuthorityRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/authority")
public class AuthorityController {

    @Autowired
    private AuthorityRequest authorityRequest;

    @Autowired
    private AuthorityRepository authorityRepository;

    @ResponseBody
    @RequestMapping(value = "/fetchAllAuthorities", method = RequestMethod.GET)
    public ManagedUserModelApi fetchAllAuthorities(){
        return authorityRequest.fetchAllAuthorities();
    }

    @ResponseBody
    @RequestMapping(value = "/fetchAllAuthorities/list", method = RequestMethod.GET)
    public List<Authority> fetchAllAuthoritiesFromRepository(){
        return authorityRepository.findAllByAuthorityIsNotNull();
    }


    @ResponseBody
    @RequestMapping(value = "/mapAuthoritiesToUser", method = RequestMethod.POST)
    public ManagedUserModelApi mapAuthoritiesToUser(@RequestBody ManagedUserModelApi userModelApi){
        return authorityRequest.mapAuthoritiestoUser(userModelApi);
    }


    @ResponseBody
    @RequestMapping(value = "/mapAuthorityToUser", method = RequestMethod.POST)
    public ManagedUserModelApi mapAuthorityToUser(@RequestBody ManagedUserModelApi userModelApi){
        return authorityRequest.mapAuthoritytoUser(userModelApi);
    }

    @ResponseBody
    @RequestMapping(value = "/addAuthority", method = RequestMethod.POST)
    public ManagedUserModelApi addAuthorityToUser(@RequestBody Authority authority){
        return authorityRequest.addAuthority(authority);
    }

}
