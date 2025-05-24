package org.rails.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Follow {
    private int id;
    private UserProfile follower;
    private UserProfile following;
    private LocalDateTime createdAt;

    public Follow() {
    }

    public Follow(int id, UserProfile follower, UserProfile following) {
        this.id = id;
        this.follower = follower;
        this.following = following;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserProfile getFollower() {
        return follower;
    }

    public void setFollower(UserProfile follower) {
        this.follower = follower;
    }

    public UserProfile getFollowing() {
        return following;
    }

    public void setFollowing(UserProfile following) {
        this.following = following;
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
        Follow follow = (Follow) o;
        return id == follow.id && Objects.equals(follower, follow.follower) && Objects.equals(following, follow.following) && Objects.equals(createdAt, follow.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, follower, following, createdAt);
    }
}
