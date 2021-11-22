package com.yufu.identity.mfa;

import com.j256.twofactorauth.TimeBasedOneTimePasswordUtil;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;

/**
 * @author wang
 * @date 2021/11/22 23:51
 */
@Service
public class MfaService {

    public boolean check(String hexKey, String code) {
        try {
            return TimeBasedOneTimePasswordUtil.validateCurrentNumberHex(hexKey, Integer.parseInt(code), 10000);
        }
        catch (GeneralSecurityException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
}
