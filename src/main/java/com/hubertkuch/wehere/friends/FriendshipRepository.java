package com.hubertkuch.wehere.friends;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<FriendshipEntity, String> {
    Optional<FriendshipEntity> findByStatusAndFirstFriendId_IdOrSecondFriendId_Id(
            FriendshipApprovalStatus status,
            String firstFriendId,
            String secondFriendId
    );
}
