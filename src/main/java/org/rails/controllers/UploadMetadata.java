package org.rails.controllers;

public class UploadMetadata {
    private int userId;
    private String caption;

    public UploadMetadata() {
    }

    public UploadMetadata(int userId, String caption) {
        this.userId = userId;
        this.caption = caption;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public String toString() {
        return "UploadMetadata{" +
                "userId=" + userId +
                ", caption='" + caption + '\'' +
                '}';
    }
}
