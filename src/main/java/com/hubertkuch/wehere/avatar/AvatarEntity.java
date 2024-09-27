package com.hubertkuch.wehere.avatar;

import jakarta.persistence.*;

@Entity
@Table(name = "avatar")
public class AvatarEntity {
    @Id
    @Column(name = "account_id")
    private String id;
    private String avatar;
    @Column(name = "thumbnail_32_32")
    private String thumbnail32x32;
    @Column(name = "thumbnail_126_126")
    private String thumbnail126x126;

    public AvatarEntity(String id, String avatar, String thumbnail32x32, String thumbnail126x126) {
        this.id = id;
        this.avatar = avatar;
        this.thumbnail32x32 = thumbnail32x32;
        this.thumbnail126x126 = thumbnail126x126;
    }

    public AvatarEntity() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getThumbnail32x32() {
        return thumbnail32x32;
    }

    public void setThumbnail32x32(String thumbnail32x32) {
        this.thumbnail32x32 = thumbnail32x32;
    }

    public String getThumbnail126x126() {
        return thumbnail126x126;
    }

    public void setThumbnail126x126(String thumbnail126x126) {
        this.thumbnail126x126 = thumbnail126x126;
    }
}
