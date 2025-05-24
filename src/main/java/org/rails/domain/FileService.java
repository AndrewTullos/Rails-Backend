package org.rails.domain;

import org.rails.models.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    FileUploadResponse uploadFile(MultipartFile multipartFile);
}