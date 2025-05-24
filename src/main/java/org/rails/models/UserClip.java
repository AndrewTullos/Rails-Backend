package org.rails.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class UserClip {
    private int clipId;
    private UserProfile userProfile;
    private String mediaUrl;
    private String caption;
    private LocalDateTime createdAt;

    public UserClip() {
    }

    public UserClip(int clipId, UserProfile userProfile, String mediaUrl, String caption) {
        this.clipId = clipId;
        this.userProfile = userProfile;
        this.mediaUrl = mediaUrl;
        this.caption = caption;
    }

    public int getClipId() {
        return clipId;
    }

    public void setClipId(int clipId) {
        this.clipId = clipId;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserClip userClip = (UserClip) o;
        return clipId == userClip.clipId && Objects.equals(userProfile, userClip.userProfile) && Objects.equals(mediaUrl, userClip.mediaUrl) && Objects.equals(caption, userClip.caption) && Objects.equals(createdAt, userClip.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clipId, userProfile, mediaUrl, caption, createdAt);
    }
}
