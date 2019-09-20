package com.searker.search.engine.service;

import com.searker.search.engine.model.Document;

import java.util.List;

public interface DocumentManager {

    Document saveDocument(Document document);

    Document retrieveDocument(String id);

    List<Document> retrieveDocuments();

}
