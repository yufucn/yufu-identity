package com.yufu.identity.service;

/**
 * @author wang
 * @date 2021/7/5 23:12
 */
public interface YufuUserService {
    void updateAccessFailedCountOrLock(String userName);

    void resetAccessFailedCount(String userName);
}
