package com.hubertkuch.wehere.friends;

import com.hubertkuch.wehere.account.Account;

import java.time.Instant;

public record Friendship(String id, Account firstOne, Account secondOne, Instant createdAt, FriendshipApprovalStatus friendshipApprovalStatus) {
    public static Friendship from(FriendshipEntity entity) {
        return new Friendship(entity.getId(),
                Account.from(entity.getFirstFriend()),
                Account.from(entity.getSecondFriend()),
                entity.getCreatedAt(),
                entity.getStatus()
        );
    }
}
