package com.example.sp.controller;

import com.example.sp.pojo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file)throws IOException {
        String originalFilename = file.getOriginalFilename();
        //借助uuid时文件名唯一，放止被覆盖
        String filename = UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));
        file.transferTo(new File("E:\\java project\\2024_2_6_sp\\file\\" + filename));
        return Result.success("https://"+filename);
    }
}
