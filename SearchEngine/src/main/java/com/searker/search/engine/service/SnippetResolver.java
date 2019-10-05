package com.searker.search.engine.service;

import com.searker.search.engine.model.IndexedDocument;
import com.searker.search.engine.model.SearchRequest;
import com.searker.search.engine.model.Snippet;

import java.util.List;

public interface SnippetResolver {

    List<Snippet> resolveSnippets(SearchRequest searchRequest, IndexedDocument indexedDocument);

}
