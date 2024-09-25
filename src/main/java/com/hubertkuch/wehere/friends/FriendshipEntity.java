package com.hubertkuch.wehere.friends;

import com.hubertkuch.wehere.account.AccountEntity;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "friendship")
public class FriendshipEntity {
    @Id
    private String id = UUID.randomUUID().toString();
    @ManyToOne
    @JoinColumn(name = "first_friend_id", referencedColumnName = "id")
    private AccountEntity firstFriend;
    @ManyToOne
    @JoinColumn(name = "second_friend_id", referencedColumnName = "id")
    private AccountEntity secondFriend;
    private Instant createdAt = Instant.now();

    @Enumerated(EnumType.STRING)
    private FriendshipApprovalStatus status;

    public FriendshipEntity(
            AccountEntity firstFriend, AccountEntity secondFriendId
    ) {
        this.firstFriend = firstFriend;
        this.secondFriend = secondFriendId;
        this.status = FriendshipApprovalStatus.PENDING;
    }

    public FriendshipEntity() {}

    public FriendshipEntity(
            String id,
            AccountEntity firstFriend,
            AccountEntity secondFriend,
            Instant createdAt,
            FriendshipApprovalStatus status
    ) {
        this.id = id;
        this.firstFriend = firstFriend;
        this.secondFriend = secondFriend;
        this.createdAt = createdAt;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AccountEntity getFirstFriend() {
        return firstFriend;
    }

    public void setFirstFriend(AccountEntity firstFriendId) {
        this.firstFriend = firstFriendId;
    }

    public AccountEntity getSecondFriend() {
        return secondFriend;
    }

    public void setSecondFriend(AccountEntity secondFriendId) {
        this.secondFriend = secondFriendId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public FriendshipApprovalStatus getStatus() {
        return status;
    }

    public void setStatus(FriendshipApprovalStatus status) {
        this.status = status;
    }
}