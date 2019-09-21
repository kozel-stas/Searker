package com.searker.search.engine.service;

import com.searker.search.engine.model.SearchRequest;

public interface SearchQueryIndexator {

    double[] indexSearchQuery(SearchRequest searchRequest);

}
