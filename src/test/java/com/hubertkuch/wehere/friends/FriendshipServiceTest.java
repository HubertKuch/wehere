package com.hubertkuch.wehere.friends;

import com.hubertkuch.wehere.account.*;
import com.hubertkuch.wehere.friends.exceptions.CannotFindFriendshipException;
import com.hubertkuch.wehere.friends.exceptions.CannotMakeFriendshipException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FriendshipServiceTest {

    @Mock
    AccountService accountService;
    @Mock
    AccountRepository accountRepository;
    @Mock
    FriendshipRepository friendshipRepository;
    FriendshipService friendshipService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        this.friendshipService = new FriendshipService(accountService, accountRepository, friendshipRepository);
    }

    @Test
    void shouldReturnValidFriendship() throws CannotMakeFriendshipException {
        when(accountService.getAccountByHashtag(anyString())).thenReturn(new Account("diffId", "", "", "", Gender.MALE));
        when(friendshipRepository.findAlreadyExistingRequest(anyString(),
                anyString()
        )).thenReturn(Optional.empty());
        when(accountRepository.findById(anyString())).thenReturn(Optional.of(new AccountEntity()));
        when(friendshipRepository.save(any(FriendshipEntity.class))).thenReturn(new FriendshipEntity());

        var friendship = friendshipService.makeFriendship("", "");

        assertNotNull(friendship);
    }

    @Test
    void shouldThrowExceptionWhenHashtagIsInvalid() {
        when(accountService.getAccountByHashtag(anyString())).thenReturn(null);

        assertThrows(CannotMakeFriendshipException.class, () -> friendshipService.makeFriendship("", ""));
    }

    @Test
    void shouldThrowExceptionWhenRequestWasAlreadySent() {
        when(accountService.getAccountByHashtag(anyString())).thenReturn(new Account("", "", "", "", Gender.MALE));
        when(friendshipRepository.findAlreadyExistingRequest(anyString(), anyString())).thenReturn(Optional.of(new FriendshipEntity()));

        assertThrows(CannotMakeFriendshipException.class, () -> friendshipService.makeFriendship("", ""));
    }

    @Test
    void shouldThrowCannotMakeFriendshipExceptionWhenHashtagedUserHasTheSameIdAsInitiator() {
        when(accountService.getAccountByHashtag(anyString())).thenReturn(new Account("", "", "", "", Gender.MALE));

        assertThrows(CannotMakeFriendshipException.class, () -> friendshipService.makeFriendship("", ""));
    }

    @Test
    void shouldAcceptFriendship() throws CannotMakeFriendshipException, CannotFindFriendshipException {
        when(friendshipRepository.findById(anyString())).thenAnswer(invocationOnMock -> {
            var friendshipEntity = new FriendshipEntity();
            var secondOne = new AccountEntity();
            secondOne.setId("id");
            friendshipEntity.setSecondFriend(secondOne);
            friendshipEntity.setFirstFriend(new AccountEntity());

            return Optional.of(friendshipEntity);
        });

        var friendship = friendshipService.acceptFriendship("id", "friendship_id");

        assertNotNull(friendship);
        assertEquals(FriendshipApprovalStatus.ACCEPTED, friendship.friendshipApprovalStatus());
    }

    @Test
    void shouldThrowCannotMakeFriendshipExceptionWhenReceiverIdIsDifferent() {
        when(friendshipRepository.findById(anyString())).thenAnswer(invocationOnMock -> {
            var friendshipEntity = new FriendshipEntity();
            var secondOne = new AccountEntity();
            secondOne.setId("id");
            friendshipEntity.setSecondFriend(secondOne);

            return Optional.of(friendshipEntity);
        });

        assertThrows(CannotMakeFriendshipException.class, () -> friendshipService.acceptFriendship("some_diff_id", "friendship_id"));
    }

    @Test
    void shouldThrowCannotFindFriendshipExceptionWhenIdIsWrong() {
        when(friendshipRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(CannotFindFriendshipException.class, () -> friendshipService.acceptFriendship("", "friendship_id"));
    }
}