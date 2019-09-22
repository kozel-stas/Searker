package com.searker.search.engine.service;

import com.searker.search.engine.model.SearchResult;

import java.util.List;

public interface MetricManager {

    SearchResult register(SearchResult searchResult);

    List<SearchResult> retrieveMetrics();

}
