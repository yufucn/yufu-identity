package com.yufu.identity.mfa;

import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;

/**
 * @author wang
 * @date 2021/11/23 0:01
 */
public class MfaTrustResolver implements AuthenticationTrustResolver {

    private final AuthenticationTrustResolver delegate = new AuthenticationTrustResolverImpl();

    @Override
    public boolean isAnonymous(Authentication authentication) {
        return this.delegate.isAnonymous(authentication) || authentication instanceof MfaAuthentication;
    }

    @Override
    public boolean isRememberMe(Authentication authentication) {
        return this.delegate.isRememberMe(authentication);
    }
}
