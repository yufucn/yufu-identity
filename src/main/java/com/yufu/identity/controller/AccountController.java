package com.yufu.identity.controller;

import com.yufu.identity.config.YufuUserDetailsService;
import com.yufu.identity.entity.YufuUser;
import lombok.AllArgsConstructor;
import lombok.var;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wang
 */
@Controller
@AllArgsConstructor
public class AccountController {

    private final YufuUserDetailsService yufuUserService;

    @GetMapping("/oauth2/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            HttpServletRequest request,
            Model model) {
        model.addAttribute("loginUrl", "/oauth2/login");
        if (error != null) {
            model.addAttribute("error", getErrorMessage(request));
        }
        return "login";
    }

    @PostMapping("/oauth2/register")
    public String register(YufuUser user) {
        yufuUserService.register(user);
        return "redirect:/oauth2/login";
    }

    @GetMapping("/oauth2/register")
    public String registerPage() {
        return "register";
    }

    private String getErrorMessage(HttpServletRequest request) {
        var exception = (Exception) request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        var error = "";
        if (exception instanceof BadCredentialsException) {
            error = "用户名或密码不正确";
        } else if (exception instanceof LockedException) {
            error = "用户已被锁定";
        } else {
            error = "用户名或密码不正确";
        }
        return error;
    }
}
