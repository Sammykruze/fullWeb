package com.samuelCode.fullWeb.repository;

import com.samuelCode.fullWeb.entity.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface FileUploadRepo extends JpaRepository<FileUpload, Long> {

    Optional<FileUpload> findById(Long id);
}
