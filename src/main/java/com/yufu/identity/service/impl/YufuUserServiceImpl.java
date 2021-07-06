package com.yufu.identity.service.impl;

import com.yufu.identity.repository.YufuUserRepository;
import com.yufu.identity.service.YufuUserService;
import lombok.AllArgsConstructor;
import lombok.var;
import org.springframework.stereotype.Service;

/**
 * @author wang
 * @date 2021/7/5 23:13
 */
@Service
@AllArgsConstructor
public class YufuUserServiceImpl implements YufuUserService {

    private final YufuUserRepository yufuUserRepository;
    private static final int MAX_FAILED_COUNT = 5;

    @Override
    public void updateAccessFailedCountOrLock(String userName) {
        var userOptional = yufuUserRepository.findByUserName(userName);
        if (userOptional.isPresent()) {
            var user = userOptional.get();
            var count = user.getAccessFailedCount();
            if (count == null) {
                count = 0;
            }
            if (count + 1 >= MAX_FAILED_COUNT) {
                user.setAccountNonLocked(false);
            }
            user.setAccessFailedCount(count + 1);
            yufuUserRepository.save(user);
        }
    }

    @Override
    public void resetAccessFailedCount(String userName) {
        var userOptional = yufuUserRepository.findByUserName(userName);
        if (userOptional.isPresent()) {
            var user = userOptional.get();
            if (user.getAccessFailedCount() > 0) {
                user.setAccessFailedCount(0);
                yufuUserRepository.save(user);
            }
        }
    }
}
