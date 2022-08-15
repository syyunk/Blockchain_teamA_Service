package com.ateam.popserver.common.utils;//package com.ateam.popmarket.common.utils;
//
//import com.ateam.popmarket.common.dto.FileResponseDTO;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.core.io.Resource;
//import org.springframework.http.ContentDisposition;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Objects;
//import java.util.UUID;
//
//@Service
//public class FileAssistProvider {
//    @Value("${com.shoppingmall.picture.upload.path}")
//    private String uploadPath;
//
//    public String makeFolder(String targetDirectory) {
//        String str = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
//
//        String realUploadPath = targetDirectory + File.separator + str.replace("/", "\\");
//
//        File uploadPathDir = new File(uploadPath, realUploadPath);
//
//        if(!uploadPathDir.exists()) {
//            uploadPathDir.mkdirs();
//        }
//
//        return realUploadPath;
//    }
//
//    public FileResponseDTO uploadFile(String targetDirectory, MultipartFile uploadFile) {
//        if(!Objects.requireNonNull(uploadFile.getContentType()).startsWith("image")) {
//            return FileResponseDTO.builder().isSuccess(false).error("이미지 파일을 업로드하셔야 합니다.").build();
//        }
//        String originalName = uploadFile.getOriginalFilename();
//        String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
//
//        String uuid = UUID.randomUUID().toString()
//                .replace("/", "1")
//                .replace("\\", "1");
//
//        String saveName = uploadPath + File.separator + targetDirectory + File.separator + uuid + fileName;
//
//        Path savePath = Paths.get(saveName);
//
//        try {
//            uploadFile.transferTo(savePath);
//        } catch (Exception e) {
//            System.out.println(e.getLocalizedMessage());
//        }
//
//        return FileResponseDTO.builder().isSuccess(true).uuid(uuid).fileName(fileName).realUploadPath(targetDirectory).build();
//    }
//
//    public ResponseEntity<Object> download(String targetDirectory, String path) {
//        Path filePath = Paths.get(uploadPath + File.separator + targetDirectory + File.separator + path);
//
//        try {
//            Resource resource = new InputStreamResource(Files.newInputStream(filePath));
//
//            File file = new File(path);
//            HttpHeaders headers = new HttpHeaders();
//
//            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(file.getName()).build());
//
//            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
//        } catch (IOException e) {
//            return new ResponseEntity<>(null, HttpStatus.OK);
//        }
//    }
//}
//
