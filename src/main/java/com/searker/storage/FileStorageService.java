package com.searker.storage;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@PropertySource("application.properties")
public class FileStorageService implements StorageService, InitializingBean {

    @Value("${file.storage.path}")
    private String uploadDir;
    private Path fileStorageLocation;
    private final ResourceLoader resourceLoader = new FileSystemResourceLoader();

    public FileStorageService() {
    }

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(UUID.randomUUID().toString() + file.getOriginalFilename());
        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource uploadResource(String fileName) {
        try {
            return resourceLoader.getResource(this.fileStorageLocation.resolve(fileName).normalize().toUri().toString());
        } catch (RuntimeException ex) {
            throw new FileNotFoundException("File not found " + fileName, ex);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(this.fileStorageLocation);
    }


}
