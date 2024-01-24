// package com.commercial.commerce.sale.controller;

// import java.io.File;
// import java.io.IOException;
// import java.time.LocalDateTime;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.multipart.MultipartFile;

// import com.commercial.commerce.UserAuth.Service.RefreshTokenService;
// import com.commercial.commerce.response.ApiResponse;
// import com.commercial.commerce.response.Status;
// import com.commercial.commerce.sale.entity.FilesBody;
// import com.commercial.commerce.sale.service.IImageService;
// import com.commercial.commerce.sale.service.TypeService;

// import lombok.RequiredArgsConstructor;

// @RestController
// @RequestMapping("/actu")
// @RequiredArgsConstructor
// public class UploadController extends Controller {
// // @PostMapping("/file")
// // public ResponseEntity<ApiResponse<FilesBody>> uploadFile(@RequestBody
// // FilesBody files) {
// // try {
// // FileHelper file = new FileHelper();
// // for (String fileBase64 : files.getFiles()) {
// // file.upload(fileBase64);
// // }
// // return createResponseEntity(files, "file upload successfully");

// // } catch (Exception e) {
// // return ResponseEntity.status(HttpStatus.OK)
// // .body(new ApiResponse<>(null, new Status("error", e.getMessage()),
// // LocalDateTime.now()));
// // }
// // }s
// @Autowired
// IImageService imageService;

// @PostMapping("/file")
// public ResponseEntity create(@RequestParam(name = "file") MultipartFile[]
// files) {

// for (MultipartFile file : files) {

// try {

// String fileName = imageService.save(file);

// String imageUrl = imageService.getImageUrl(fileName);

// // do whatever you want with that

// } catch (Exception e) {
// // throw internal error;
// }
// }

// return ResponseEntity.ok().build();
// }

// }
