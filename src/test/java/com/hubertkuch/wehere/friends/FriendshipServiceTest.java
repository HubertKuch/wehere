package com.hubertkuch.wehere.friends;

import com.hubertkuch.wehere.account.*;
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
        when(accountService.getAccountByHashtag(anyString())).thenReturn(new Account("", "", "", "", Gender.MALE));
        when(friendshipRepository.findAlreadyExistingRequest(anyString(),
                anyString()
        )).thenReturn(Optional.of(new FriendshipEntity()));
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
        when(friendshipRepository.findAlreadyExistingRequest(anyString(), anyString())).thenReturn(Optional.empty());

        assertThrows(CannotMakeFriendshipException.class, () -> friendshipService.makeFriendship("", ""));
    }
}