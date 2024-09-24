package com.hubertkuch.wehere.friends;

import com.hubertkuch.wehere.account.AccountRepository;
import com.hubertkuch.wehere.account.AccountService;
import com.hubertkuch.wehere.friends.exceptions.CannotMakeFriendshipException;
import org.springframework.stereotype.Service;

@Service
public record FriendshipService(
        AccountService accountService, AccountRepository accountRepository, FriendshipRepository friendshipRepository
) {

    /**
     * Makes a friendship between initiator and hashtag account
     *
     * @throws CannotMakeFriendshipException when hashtag is invalid or request was already sent but status is pending
     */
    public Friendship makeFriendship(String initiatorId, String hashtag) throws CannotMakeFriendshipException {
        var hashtagAccount = accountService.getAccountByHashtag(hashtag);

        if (hashtagAccount == null) throw new CannotMakeFriendshipException("Invalid hashtag of friend");

        if (friendshipRepository.findByStatusAndFirstFriendId_IdOrSecondFriendId_Id(FriendshipApprovalStatus.PENDING,
                initiatorId,
                hashtagAccount.id()
        ).isEmpty()) {
            throw new CannotMakeFriendshipException("Request already sent");
        }

        var entity = new FriendshipEntity(accountRepository.findById(initiatorId).get(),
                accountRepository.findById(hashtagAccount.id()).get()
        );

        friendshipRepository.save(entity);

        return Friendship.from(entity);
    }
}
