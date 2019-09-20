package com.searker.rest;

import com.searker.rest.model.Document;
import com.searker.rest.service.URLResolver;
import com.searker.search.engine.service.DocumentManager;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/document")
public class DocumentController {

    private final DocumentManager documentManager;
    private final URLResolver urlResolver;

    public DocumentController(DocumentManager documentManager, URLResolver urlResolver) {
        this.documentManager = documentManager;
        this.urlResolver = urlResolver;
    }

    @RequestMapping(
            value = "{id}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Document> document(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok(new Document(documentManager.retrieveDocument(id), urlResolver));
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<Document>> documents() {
        List<Document> documents = documentManager.retrieveDocuments().stream()
                .map(document -> new Document(document, urlResolver)).collect(Collectors.toList());
        return ResponseEntity.ok(documents);
    }

    @RequestMapping(
            method = RequestMethod.POST
    )
    public ResponseEntity<Document> newDocument(@RequestBody @Valid Document document) {
        com.searker.search.engine.model.Document doc = new com.searker.search.engine.model.Document();
        doc.setOriginalLocation(document.getLocation());
        doc.setTitle(document.getTitle());
        doc.setDate(new Date());
        return ResponseEntity.ok(new Document(documentManager.saveDocument(doc), urlResolver));
    }

}
