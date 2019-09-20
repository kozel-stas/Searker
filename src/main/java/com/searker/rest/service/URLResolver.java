package com.searker.rest.service;

import com.searker.search.engine.model.Document;

public interface URLResolver {

    String resolveDocumentLocationURL(Document document);

    String resolveOriginalDocumentLocationURL(Document document);

}
