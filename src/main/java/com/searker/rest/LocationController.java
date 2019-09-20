package com.searker.rest;

import com.searker.search.engine.service.DocumentManager;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/location")
public class LocationController {

    private final DocumentManager documentManager;

    public LocationController(DocumentManager documentManager) {
        this.documentManager = documentManager;
    }

    @RequestMapping(
            value = "{id}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public RedirectView document(@PathVariable(name = "id") String id) {
        return new RedirectView(documentManager.retrieveDocument(id).getOriginalLocation());
    }

}
