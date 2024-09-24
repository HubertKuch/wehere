package com.hubertkuch.wehere.controllers;

import com.hubertkuch.wehere.account.Gender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account")
public record AccountController() {

    @GetMapping("/genders")
    public Gender[] genders() {
        return Gender.values();
    }
}
