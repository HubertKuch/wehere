package com.hubertkuch.wehere.account;

import com.hubertkuch.wehere.avatar.Avatar;

public record Account(String id, String username, String password, String hashtag, Gender gender, Avatar avatar) {
    public static Account from(AccountEntity accountEntity) {
        return new Account(accountEntity.getId(),
                accountEntity.getUsername(),
                accountEntity.getPassword(),
                accountEntity.getHashtag(),
                accountEntity.getGender(),
                Avatar.from(accountEntity.getAvatar())
        );
    }
}
