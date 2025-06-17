package sreenidhi.microfinanceSystem.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sreenidhi.microfinanceSystem.service.FileStorageService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public String saveFile(MultipartFile file, String subFolder) throws IOException {
        if (file == null || file.isEmpty()) return null;

        String folderPath = uploadDir + "/" + subFolder;
        Files.createDirectories(Paths.get(folderPath));
        String filePath = folderPath + "/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();

        Path path = Paths.get(filePath);
        Files.write(path, file.getBytes());

        return filePath;
    }
}
