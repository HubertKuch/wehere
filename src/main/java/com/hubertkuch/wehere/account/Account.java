package com.hubertkuch.wehere.account;

public record Account(String id, String username, String password, String hashtag, Gender gender) {
    public static Account from(AccountEntity accountEntity) {
        return new Account(accountEntity.getId(),
                accountEntity.getUsername(),
                accountEntity.getPassword(),
                accountEntity.getHashtag(),
                accountEntity.getGender()
        );
    }
}
