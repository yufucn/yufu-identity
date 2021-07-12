package com.yufu.identity.controller;

import com.yufu.identity.entity.YufuUser;
import lombok.AllArgsConstructor;
import lombok.var;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author wang
 * @date 2021/7/7 22:20
 */
@Controller
@AllArgsConstructor
public class UserController {

    @GetMapping("/user/me")
    public String me(Model model, HttpSession session) {
        var securityContext = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        var user = (YufuUser) securityContext.getAuthentication().getPrincipal();
        model.addAttribute("userName", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "user/index";
    }
}
