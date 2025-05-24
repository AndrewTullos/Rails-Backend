package org.rails.models;

import java.time.LocalDateTime;

public class FileUploadResponse {
    private String filePath;
    private String fileUrl;
    private LocalDateTime dateTime;

    public FileUploadResponse() {
    }

    public FileUploadResponse(String filePath, String fileUrl, LocalDateTime dateTime) {
        this.filePath = filePath;
        this.fileUrl = fileUrl;
        this.dateTime = dateTime;
    }

    public FileUploadResponse(String mediaUrl) {
        this.fileUrl = mediaUrl;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "FileUploadResponse{" +
                "filePath='" + filePath + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
