package com.zeed.paaro.controller;

import com.zeed.paaro.lib.services.UserService;
import com.zeed.usermanagement.apimodels.ManagedUserModelApi;
import com.zeed.usermanagement.models.ManagedUser;
import com.zeed.usermanagement.request.UserDetailsRequest;
import com.zeed.usermanagement.requestmodels.PasswordResetRequestModel;
import com.zeed.usermanagement.requestmodels.UserUpdateRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDetailsRequest userDetailsRequester;

    @Autowired
    private UserService userService;

    @ResponseBody
    @PreAuthorize(value = "hasAnyAuthority('DEACTIVATE_USER')")
    @RequestMapping (value = "/deactivateUser", method = RequestMethod.GET)
    public ManagedUserModelApi deactivateUser(Principal principal, @RequestParam("email") String email) {
        return userDetailsRequester.deactivateUser(email);
    }

    @ResponseBody
    @PreAuthorize(value = "hasAnyAuthority('ACTIVATE_USER')")
    @RequestMapping (value = "/activateUser", method = RequestMethod.GET)
    public ManagedUserModelApi activateUser(Principal principal, @RequestParam("email") String email) {
        return userDetailsRequester.activateUser(email);
    }

    @ResponseBody
    @RequestMapping (value = "/updateUser", method = RequestMethod.POST)
    public ManagedUserModelApi updateUser(Principal principal, @RequestBody UserUpdateRequestModel requestModel) {
        return userDetailsRequester.updateUserDetails(requestModel);
    }

    @ResponseBody
    @RequestMapping (value = "/reset_user_password", method = RequestMethod.POST)
    public ManagedUserModelApi resetUserPassword(Principal principal, @RequestBody PasswordResetRequestModel requestModel) {
        return userDetailsRequester.resetUserPassword(requestModel);
    }


    @ResponseBody
    @RequestMapping (value = "/login", method = RequestMethod.POST)
    public Object login(@RequestBody ManagedUser managedUser) throws Exception {
        return userService.login(managedUser);
    }



}
