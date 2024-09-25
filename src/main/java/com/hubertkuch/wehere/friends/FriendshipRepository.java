package com.hubertkuch.wehere.friends;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface FriendshipRepository extends JpaRepository<FriendshipEntity, String> {
    @Query(value = """
                       SELECT f.* FROM friendship f WHERE\s
                   (f.status = 'PENDING' OR f.status = 'ACCEPTED')
                       AND (
                           (f.first_friend_id = :first_friend_id AND f.second_friend_id = :second_friend_id)
                           OR
                           (f.first_friend_id = :second_friend_id AND f.second_friend_id = :first_friend_id)
                       )
                  \s""", nativeQuery = true)
    Optional<FriendshipEntity> findAlreadyExistingRequest(
            @Param("first_friend_id") String firstFriendId,
            @Param("second_friend_id") String secondFriendId
    );

    @Query(nativeQuery = true,value = """
                     SELECT f.* FROM friendship f WHERE\s
                   f.status = 'ACCEPTED'
                       AND (
                           f.first_friend_id = :account_id OR f.second_friend_id = :account_id
                       )
""")
    Set<FriendshipEntity> findYourFriends(@Param("account_id") String accountId);

    @Query(nativeQuery = true,value = """
                                 SELECT f.* FROM friendship f WHERE\s
                               f.status = 'PENDING' AND f.second_friend_id = :account_id
            """)
    Set<FriendshipEntity> findYourPendingRequests(@Param("account_id") String accountId);
}
