package com.searker.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import java.util.List;

public class SearchResult {

    @JsonProperty
    private final String searchQuery;
    @JsonProperty
    private final List<DocumentSearchResult> documents;

    public SearchResult(String searchQuery, List<DocumentSearchResult> documents) {
        this.searchQuery = searchQuery;
        this.documents = ImmutableList.copyOf(documents);
    }

    public List<DocumentSearchResult> getDocuments() {
        return documents;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

}
