package org.rails.controllers;

import org.rails.models.FileUploadResponse;
import org.rails.domain.FileService;
import org.rails.domain.UserClipService;
import org.rails.models.UserClip;
import org.rails.models.UserProfile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/s3")
public class FileUploadController {

    private final FileService fileService;
    private final UserClipService service;

    public FileUploadController(FileService fileService, UserClipService service) {
        this.fileService = fileService;
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> uploadFile(
            @RequestPart("file") MultipartFile file,
            @RequestPart("metadata") UploadMetadata metadata) {

        // Handles AWS upload
        String mediaUrl = fileService.uploadFile(file).getFileUrl();

        // Create UserProfile & UserClip objects
        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(metadata.getUserId());

        UserClip userClip = new UserClip();
        userClip.setUserProfile(userProfile);
        userClip.setMediaUrl(mediaUrl);
        userClip.setCaption(metadata.getCaption());

        service.create(userClip);

        return new ResponseEntity<>(new FileUploadResponse(mediaUrl), HttpStatus.OK);
    }
}
