package com.commercial.commerce.chat.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UploadService {

    String uploadFolder = "./uploads";

    public void upload(MultipartFile multipartFile) {
        File folder = checkFolder(uploadFolder);

        String newFileName = multipartFile.getOriginalFilename();

        folder = new File(removeLocalMark(folder.getAbsolutePath()) + File.separator + newFileName + "."
                + getFileExtension(multipartFile.getOriginalFilename()));

        try {
            byte[] data = multipartFile.getBytes();
            Path path = Paths.get(folder.getAbsolutePath());
            Files.write(path, data);
            System.out.println(path.toAbsolutePath().toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private String removeLocalMark(String text) {
        return text.replace(".\\", "");
    }

    private File checkFolder(String folderName) {
        File folder = new File(uploadFolder);
        if (!folder.exists())
            folder.mkdir();
        return folder;
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        } else {
            return "";
        }
    }
}
