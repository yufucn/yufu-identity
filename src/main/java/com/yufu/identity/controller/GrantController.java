package com.yufu.identity.controller;

import lombok.var;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author wang
 */
@Controller
@SessionAttributes("authorizationRequest")
public class GrantController {

    @RequestMapping("/oauth2/confirm_access")
    public String confirmAccess(
            Map<String, Object> param,
            HttpServletRequest request,
            Model model) throws Exception {
        var authorizationRequest = (AuthorizationRequest) param.get("authorizationRequest");
        if (authorizationRequest == null) {
            return "redirect:" + "/oauth2/login";
        }
        var clientId = authorizationRequest.getClientId();
        model.addAttribute("scopes", authorizationRequest.getScope());
        model.addAttribute("client", clientId);
        return "grant";
    }
}
