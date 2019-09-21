package com.searker.rest;

import com.searker.rest.model.Location;
import com.searker.search.engine.service.DocumentManager;
import org.apache.catalina.webresources.FileResource;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDataSourceConfiguration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/location")
public class LocationController {

    private final DocumentManager documentManager;
    private final ResourceLoader resourceLoader = new DefaultResourceLoader();

    public LocationController(DocumentManager documentManager) {
        this.documentManager = documentManager;
    }

    @RequestMapping(
            value = "{id}",
            method = RequestMethod.GET
    )
    public Resource location(@PathVariable(name = "id") String id) {
        return resourceLoader.getResource(documentManager.retrieveDocument(id).getOriginalLocation());
    }

    @RequestMapping(
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Location> document(@RequestParam("file")  MultipartFile file) {
        return ResponseEntity.ok(new Location("wdwd"));
    }

}
