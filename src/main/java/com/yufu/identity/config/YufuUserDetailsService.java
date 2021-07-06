package com.yufu.identity.config;

import com.yufu.identity.entity.YufuUser;
import com.yufu.identity.repository.YufuUserRepository;
import lombok.var;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Locale;

/**
 * @author wang
 */
@Component(value = "yufuUserDetailsService")
public class YufuUserDetailsService implements UserDetailsService {

    private final YufuUserRepository yufuUserRepository;
    private final PasswordEncoder passwordEncoder;

    public YufuUserDetailsService(YufuUserRepository yufuUserRepository,
                                  PasswordEncoder passwordEncoder) {
        this.yufuUserRepository = yufuUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //http://localhost:3000/oauth/authorize?client_id=user-client&response_type=code&scope=all&redirect_uri=http://www.baidu.com
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var optionalUser = yufuUserRepository.findByUserName(username);
        if(!optionalUser.isPresent()){
            optionalUser = yufuUserRepository.findByEmail(username);
            if(optionalUser.isPresent()){
                return optionalUser.get();
            }
        } else {
            return optionalUser.get();
        }
        return null;
    }

    public void register(YufuUser user) {
        var encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        user.setEnabled(true);
        user.setEmailConfirmed(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setNormalizedUserName(user.getUsername().toUpperCase(Locale.ROOT));
        user.setNormalizedEmail(user.getEmail().toUpperCase(Locale.ROOT));
        yufuUserRepository.save(user);
    }
}
