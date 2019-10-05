package com.searker.search.engine.service;

import com.searker.search.engine.model.SearchRequest;
import com.searker.search.engine.model.SearchResult;

public interface SearchEngine {

    SearchResult search(SearchRequest searchRequest);

}
