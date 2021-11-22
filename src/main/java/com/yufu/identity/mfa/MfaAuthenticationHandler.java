package com.yufu.identity.mfa;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wang
 * @date 2021/11/23 0:02
 */
public class MfaAuthenticationHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

    private final AuthenticationSuccessHandler successHandler;

    public MfaAuthenticationHandler(String url) {
        SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler(url);
        successHandler.setAlwaysUseDefaultTargetUrl(true);
        this.successHandler = successHandler;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        Authentication anonymous = new AnonymousAuthenticationToken("key", "anonymousUser",
                AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
        saveMfaAuthentication(request, response, new MfaAuthentication(anonymous));
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        saveMfaAuthentication(request, response, authentication);
    }

    private void saveMfaAuthentication(HttpServletRequest request, HttpServletResponse response,
                                       Authentication authentication) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(new MfaAuthentication(authentication));
        this.successHandler.onAuthenticationSuccess(request, response, authentication);
    }
}