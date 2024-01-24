package com.commercial.commerce.chat.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import com.commercial.commerce.chat.Utils.Json;
import com.commercial.commerce.chat.Utils.RequestAPI;
import com.commercial.commerce.chat.model.JsonResponse;

public class FileHelper {

    String uploadFolder = "./uploads";
    String API_KEY = "9d5356687ea8fd88fe2f0c58caee2402";
    String URL_Server_BB = "https://api.imgbb.com/1/upload";

    public final static String PNG = "png";
    public final static String JPG = "jpg";
    public final static String JPEG = "jpeg";

    public final static String PDF = "pdf";

    public final static String MP3 = "mp3";
    public final static String MP4 = "mp4";

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

    public JsonResponse uploadOnline(String base64Image) {
        String key = "key=" + API_KEY;
        try {
            String res = RequestAPI.sendFormData(URL_Server_BB + "?" + key, base64Image);
            JsonResponse jsonResponse = (JsonResponse) Json.fromJson(res, JsonResponse.class);
            return jsonResponse;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
