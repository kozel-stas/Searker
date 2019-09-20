package com.searker.rest.service;

import com.searker.search.engine.model.Document;
import org.springframework.stereotype.Service;

@Service
public class URLResolverImpl implements URLResolver {

    @Override
    public String resolveDocumentLocationURL(Document document) {
        return "/document/" + document.getId();
    }

    @Override
    public String resolveOriginalDocumentLocationURL(Document document) {
        return "/location/" + document.getId();
    }
}
