package com.hubertkuch.wehere.account;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
public final class Account implements UserDetails {
    @Id
    private String id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    public Account(String id, String username, String password, Gender gender) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.gender = gender;
    }

    public Account() {}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public String getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(String id) {
        this.id = id;
    }
}
