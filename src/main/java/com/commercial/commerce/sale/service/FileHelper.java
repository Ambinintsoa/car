package com.commercial.commerce.sale.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class FileHelper {

    String uploadFolder = "https://car-production-005c.up.railway.app/photo";

    // public final static String PNG = "png";
    public final static String JPG = "jpg";
    // public final static String JPEG = "jpeg";
    // public final static String PDF = "pdf";
    // public final static String MP3 = "mp3";
    // public final static String MP4 = "mp4";

    public String upload(String base64Image) throws Exception {

        File folder = checkFolder(uploadFolder);
        String fileName = System.currentTimeMillis() + "";
        folder = new File(removeLocalMark(folder.getAbsolutePath()) + File.separator + fileName + "."
                + JPG);

        try {
            byte[] bytesDecoded = Base64.getDecoder().decode(base64Image);
            Path path = Paths.get(folder.getAbsolutePath());
            Files.write(path, bytesDecoded);// manoratra anlleh fichier sy miteny oe foronina aiza le fichier
            System.out.println(path.toAbsolutePath().toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return fileName;
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

}
