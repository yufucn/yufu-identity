package com.yufu.identity.repository;

import com.yufu.identity.entity.YufuUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author wang
 * @date 2021/7/4 18:11
 */
@Repository
public interface YufuUserRepository extends CrudRepository<YufuUser, Long> {
    Optional<YufuUser> findByUserName(String userName);
    Optional<YufuUser> findByEmail(String email);
}
