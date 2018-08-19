package com.zeed.paaro.viewcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class AuthorityViewController {

    @RequestMapping("/view-authorities")
    public String viewAuthorities() {
        return "authority/authority";
    }

}
