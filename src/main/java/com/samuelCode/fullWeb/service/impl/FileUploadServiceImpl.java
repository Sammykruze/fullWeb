package com.samuelCode.fullWeb.service.impl;

import com.samuelCode.fullWeb.entity.FileUpload;
import com.samuelCode.fullWeb.exception.CustomException;
import com.samuelCode.fullWeb.repository.FileUploadRepo;
import com.samuelCode.fullWeb.service.FileUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private FileUploadRepo fileUploadRepo;
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadServiceImpl.class);
    @Override
    public FileUpload save(FileUpload fileUpload) {

        try{
            Optional<FileUpload> option = fileUploadRepo.findById(fileUpload.getId());
            if(option.isPresent()){
                fileUploadRepo.deleteById(option.get().getId());
            }

            fileUpload.setDateAdded(LocalDateTime.now());
            return fileUploadRepo.save(fileUpload);

        }catch (Exception e){
            LOGGER.info("Error::: {}, Status {} and {}", e.getMessage(), 3);
            throw new CustomException(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
