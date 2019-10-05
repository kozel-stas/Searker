package com.searker.rest;

import com.searker.storage.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLConnection;

@RestController
@RequestMapping("/filesystem")
public class FileSystemController {

    private final StorageService storageService;

    public FileSystemController(StorageService storageService) {
        this.storageService = storageService;
    }

    @RequestMapping(
            value = "/{path}",
            method = RequestMethod.GET
    )
    public ResponseEntity<Resource> resolveResource(@PathVariable(name = "path") String path) throws IOException {
        Resource resource = storageService.uploadResource(path);
        String mimeType = URLConnection.guessContentTypeFromName(resource.getFile().getName());
        return ResponseEntity.ok().contentType(MediaType.asMediaType(MimeType.valueOf(mimeType))).body(resource);
    }

}
