package com.hubertkuch.wehere.friends;

import com.hubertkuch.wehere.account.AccountRepository;
import com.hubertkuch.wehere.account.AccountService;
import com.hubertkuch.wehere.controllers.FriendshipController;
import com.hubertkuch.wehere.friends.exceptions.CannotFindFriendshipException;
import com.hubertkuch.wehere.friends.exceptions.CannotMakeFriendshipException;
import io.micrometer.observation.ObservationFilter;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public record FriendshipService(
        AccountService accountService, AccountRepository accountRepository, FriendshipRepository friendshipRepository
) {

    /**
     * Makes a friendship between initiator and hashtag account
     *
     * @throws CannotMakeFriendshipException when hashtag is invalid, request was already sent but status is PENDING or ACCEPTED and when hashtaged user has the same id as initiator
     *
     */
    public Friendship makeFriendship(String initiatorId, String hashtag) throws CannotMakeFriendshipException {
        var hashtagAccount = accountService.getAccountByHashtag(hashtag);

        if (hashtagAccount == null) throw new CannotMakeFriendshipException("Invalid hashtag of friend");

        if (initiatorId.equals(hashtagAccount.id())) {
            throw new CannotMakeFriendshipException("You can't make a friendship with yourself");
        }

        if (friendshipRepository.findAlreadyExistingRequest(
                initiatorId,
                hashtagAccount.id()
        ).isPresent()) {
            throw new CannotMakeFriendshipException("Request already sent");
        }

        var entity = new FriendshipEntity(accountRepository.findById(initiatorId).get(),
                accountRepository.findById(hashtagAccount.id()).get()
        );

        friendshipRepository.save(entity);

        return Friendship.from(entity);
    }

    public Set<Friendship> friendships(String accountId) {
        return friendshipRepository.findYourFriends(accountId).stream().map(Friendship::from).collect(Collectors.toSet());
    }

    /**
     * Accepts a friendship request. Only receiver of request can accept request.
     *
     * @throws CannotFindFriendshipException when <b>friendshipId</b> is invalid
     * @throws CannotMakeFriendshipException when id of <b>requestReceiverId</b> is different of <b>friendship second one id</b>
     */
    public Friendship acceptFriendship(String requestReceiverId, String friendshipId) throws CannotFindFriendshipException, CannotMakeFriendshipException {
        FriendshipEntity friendshipEntity = friendshipRepository.findById(friendshipId).orElseThrow(CannotFindFriendshipException::new);

        if (!friendshipEntity.getSecondFriend().getId().equals(requestReceiverId)) {
            throw new CannotMakeFriendshipException("Only receiver can accept a friendship request");
        }

        friendshipEntity.setStatus(FriendshipApprovalStatus.ACCEPTED);

        friendshipRepository.save(friendshipEntity);

        return Friendship.from(friendshipEntity);
    }

    public Set<Friendship> pendingRequests(String secondOneId) {
        return friendshipRepository.findYourPendingRequests(secondOneId).stream().map(Friendship::from).collect(Collectors.toSet());
    }
}
