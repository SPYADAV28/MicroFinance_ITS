package sreenidhi.microfinanceSystem.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {
    String saveFile(MultipartFile file, String subFolder) throws IOException;
}
