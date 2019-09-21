package com.searker.search.engine.service;

import com.searker.search.engine.model.Document;

import java.io.IOException;

public interface DocumentWordResolver {

    String[] resolveKeywords(Document document) throws Exception;

}
