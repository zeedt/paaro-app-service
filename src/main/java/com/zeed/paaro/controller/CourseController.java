package com.zeed.paaro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class CourseController {

    @RequestMapping(value = "what")
    @ResponseBody
    public Object getWhat(Principal principal) {
        return "Wow";
    }

}
