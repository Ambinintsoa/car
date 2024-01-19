package com.commercial.commerce.sale.controller;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commercial.commerce.response.ApiResponse;
import com.commercial.commerce.response.Status;
import com.commercial.commerce.sale.entity.FilesBody;
import com.commercial.commerce.sale.service.FileHelper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/actu")
@RequiredArgsConstructor
public class UploadController extends Controller {

    @PostMapping("/file")
    public ResponseEntity<ApiResponse<FilesBody>> uploadFile(@RequestBody FilesBody files) {
        try {
            FileHelper file = new FileHelper();
            for (String fileBase64 : files.getFiles()) {
                file.upload(fileBase64);
            }
            return createResponseEntity(files, "file upload successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

}
