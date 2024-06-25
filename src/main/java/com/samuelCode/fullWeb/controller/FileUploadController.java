package com.samuelCode.fullWeb.controller;

import com.samuelCode.fullWeb.entity.FileUpload;
import com.samuelCode.fullWeb.service.impl.FileUploadServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileUploadController {

    @Autowired
    FileUploadServiceImpl fileUploadService;

    @RequestMapping(method = RequestMethod.POST, value = "/save")
    public ResponseEntity<Object> upload(@RequestPart("file") MultipartFile file,
                                         @RequestBody FileUpload fileUpload)  {
        fileUploadService.save(fileUpload);
        return ResponseEntity.status(HttpStatus.OK).body("File " + fileUpload.getId() + " saved successfully");
    }

}
