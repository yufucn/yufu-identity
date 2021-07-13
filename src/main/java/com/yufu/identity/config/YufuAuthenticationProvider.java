package com.yufu.identity.config;

import com.yufu.identity.service.YufuUserService;
import lombok.var;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 认证流程扩展
 *
 * @author wang
 * @date 2021/7/5 22:46
 */
@Component
public class YufuAuthenticationProvider extends DaoAuthenticationProvider {

    @Resource
    private YufuUserService yufuUserService;

    public YufuAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        super();
        setUserDetailsService(userDetailsService);
        setPasswordEncoder(passwordEncoder);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var userName = authentication.getName();
        try {
            var auth = super.authenticate(authentication);
            yufuUserService.resetAccessFailedCount(userName);
            return auth;
        } catch (BadCredentialsException exception) {
            yufuUserService.updateAccessFailedCountOrLock(userName);
            throw exception;
        }
    }
}
