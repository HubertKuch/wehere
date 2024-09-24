package com.hubertkuch.wehere.account;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "account")
public final class AccountEntity implements UserDetails {
    @Id
    private String id;
    private String username;
    private String password;
    private String hashtag;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    public AccountEntity(String id, String username, String password, String hashtag, Gender gender) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.hashtag = hashtag;
        this.gender = gender;
    }

    public AccountEntity(String id, String username, String password, Gender gender) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.hashtag = generateHashtag();
        this.gender = gender;
    }

    public AccountEntity() {
    }

    public String generateHashtag() {
        return UUID.randomUUID().toString().substring(0, 5);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }
}
