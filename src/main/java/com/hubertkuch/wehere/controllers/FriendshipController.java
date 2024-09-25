package com.hubertkuch.wehere.controllers;

import com.hubertkuch.wehere.friends.Friendship;
import com.hubertkuch.wehere.friends.FriendshipApprovalStatus;
import com.hubertkuch.wehere.friends.FriendshipService;
import com.hubertkuch.wehere.friends.exceptions.CannotMakeFriendshipException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/friendships")
public record FriendshipController(FriendshipService friendshipService) {

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public Set<FriendshipResponse> friendships(Authentication authentication) {
        return friendshipService
                .friendships(String.valueOf(authentication.getCredentials())).stream()
                .map(FriendshipResponse::from).collect(Collectors.toSet());
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public FriendshipResponse makeFriendship(Authentication authentication, @RequestBody MakeFriendshipBody body) throws CannotMakeFriendshipException {
        return FriendshipResponse.from(friendshipService.makeFriendship(String.valueOf(authentication.getCredentials()), body.hashtag));
    }

    public record FriendshipResponse(String id,
                                     AuthController.AccountResponse firstOne,
                                     AuthController.AccountResponse secondOne,
                                     Instant createdAt,
                                     FriendshipApprovalStatus friendshipApprovalStatus
    ) {
        public static FriendshipResponse from(Friendship friendship) {
            return new FriendshipResponse(friendship.id(),
                    AuthController.AccountResponse.from(friendship.firstOne()),
                    AuthController.AccountResponse.from(friendship.secondOne()),
                    friendship.createdAt(),
                    friendship.friendshipApprovalStatus()
            );
        }
    }

    public record MakeFriendshipBody(String hashtag) {
    }
}
