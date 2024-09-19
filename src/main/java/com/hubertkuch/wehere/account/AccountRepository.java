package com.hubertkuch.wehere.account;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends ListCrudRepository<Account, String> {
    Optional<Account> findByUsername(String username);
}
