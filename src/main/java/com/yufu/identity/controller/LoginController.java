package com.yufu.identity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author wang
 * @date 2021/11/23 23:19
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    String login() {
        return "login";
    }
}
