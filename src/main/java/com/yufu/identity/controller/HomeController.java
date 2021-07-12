package com.yufu.identity.controller;

import lombok.AllArgsConstructor;
import lombok.var;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

/**
 * @author wang
 * @date 2021/7/7 22:27
 */
@Controller
@AllArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "首页");
        return "index";
    }
}
