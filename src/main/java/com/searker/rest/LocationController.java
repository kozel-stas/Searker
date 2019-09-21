package com.searker.rest;

import com.searker.rest.model.Location;
import com.searker.rest.service.URLResolver;
import com.searker.search.engine.service.DocumentManager;
import com.searker.storage.StorageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/location")
public class LocationController {

    private final DocumentManager documentManager;
    private final URLResolver urlResolver;
    private final StorageService storageService;

    public LocationController(DocumentManager documentManager, URLResolver urlResolver, StorageService storageService) {
        this.documentManager = documentManager;
        this.urlResolver = urlResolver;
        this.storageService = storageService;
    }

    @RequestMapping(
            value = "{id}",
            method = RequestMethod.GET
    )
    public RedirectView location(@PathVariable(name = "id") String id) {
        return new RedirectView(documentManager.retrieveDocument(id).getOriginalLocation());
    }

    @RequestMapping(
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Location> document(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(new Location(urlResolver.resolveUploadFileURL(storageService.storeFile(file))));
    }

}
