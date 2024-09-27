package com.hubertkuch.wehere.avatar;

public record Avatar(String id, String thumbnail32x32, String thumbnail126x126, String avatar) {
    public static Avatar from(AvatarEntity avatarEntity) {
        return new Avatar(
                avatarEntity.getId(),
                avatarEntity.getThumbnail32x32(),
                avatarEntity.getThumbnail126x126(),
                avatarEntity.getAvatar()
        );
    }
}
