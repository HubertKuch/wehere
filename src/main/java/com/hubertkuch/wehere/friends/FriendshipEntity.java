package com.hubertkuch.wehere.friends;

import com.hubertkuch.wehere.account.Account;
import com.hubertkuch.wehere.account.AccountEntity;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "friendship")
public class FriendshipEntity {
    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name = "first_friend_id", referencedColumnName = "id")
    private AccountEntity firstFriendId;
    @ManyToOne
    @JoinColumn(name = "second_friend_id", referencedColumnName = "id")
    private AccountEntity secondFriendId;
    private Instant createdAt;

    @Enumerated(EnumType.STRING)
    private StatusType status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AccountEntity getFirstFriendId() {
        return firstFriendId;
    }

    public void setFirstFriendId(AccountEntity firstFriendId) {
        this.firstFriendId = firstFriendId;
    }

    public AccountEntity getSecondFriendId() {
        return secondFriendId;
    }

    public void setSecondFriendId(AccountEntity secondFriendId) {
        this.secondFriendId = secondFriendId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }
}