package com.searker.rest.service;

import com.searker.search.engine.model.Document;

public interface URLResolver {

    String resolveDocumentLocationURL(String documentID);

    String resolveOriginalDocumentLocationURL(Document document);

}
