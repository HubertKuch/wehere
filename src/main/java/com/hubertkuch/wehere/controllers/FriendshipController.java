package com.hubertkuch.wehere.controllers;

import com.hubertkuch.wehere.friends.Friendship;
import com.hubertkuch.wehere.friends.FriendshipService;
import com.hubertkuch.wehere.friends.exceptions.CannotMakeFriendshipException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/friendships")
public record FriendshipController(FriendshipService friendshipService) {

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public Friendship makeFriendship(Authentication authentication, @RequestBody MakeFriendshipBody body) throws CannotMakeFriendshipException {
        System.out.println(authentication);
        return friendshipService.makeFriendship(String.valueOf(authentication.getCredentials()), body.hashtag);
    }

    public record MakeFriendshipBody(String hashtag) {
    }
}
