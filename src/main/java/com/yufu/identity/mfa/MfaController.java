package com.yufu.identity.mfa;

import com.yufu.identity.entity.YufuUser;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.SecureRandom;

/**
 * @author wang
 * @date 2021/11/22 23:50
 */
@Controller
public class MfaController {

    private final MfaService mfaService;

    private final BytesEncryptor encryptor;

    private final PasswordEncoder encoder;

    private final AuthenticationSuccessHandler successHandler;

    private final AuthenticationFailureHandler failureHandler;

    private final String failedAuthenticationSecret;

    private final String failedAuthenticationSecurityAnswer;

    public MfaController(MfaService mfaService,
                         BytesEncryptor encryptor,
                         PasswordEncoder encoder,
                         AuthenticationSuccessHandler successHandler,
                         AuthenticationFailureHandler failureHandler) {

        this.mfaService = mfaService;
        this.encryptor = encryptor;
        this.encoder = encoder;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.failedAuthenticationSecret = randomValue();
        this.failedAuthenticationSecurityAnswer = this.encoder.encode(randomValue());
    }

    @GetMapping("/second-factor")
    public String requestSecondFactor() {
        return "second-factor";
    }

    @GetMapping("/third-factor")
    public String requestThirdFactor() {
        return "third-factor";
    }

    private String getSecret(MfaAuthentication authentication) throws Exception {
        if (authentication.getPrincipal() instanceof YufuUser) {
//            CustomUser user = (CustomUser) authentication.getPrincipal();
//            byte[] bytes = Hex.decode(user.getSecret());
            byte[] bytes = Hex.decode("123456");
            return new String(this.encryptor.decrypt(bytes));
        }
        // earlier factor failed
        return this.failedAuthenticationSecret;
    }

    private String getAnswer(MfaAuthentication authentication) {
        if (authentication.getPrincipal() instanceof YufuUser) {
//            CustomUser user = (CustomUser) authentication.getPrincipal();
//            return user.getAnswer();
            return "answer";
        }
        // earlier factor failed
        return this.failedAuthenticationSecurityAnswer;
    }

    private static String randomValue() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        return new String(Hex.encode(bytes));
    }
}
