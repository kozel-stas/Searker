package com.searker.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import java.util.List;

public class SearchResult {

    @JsonProperty
    private final String searchQuery;

    @JsonProperty
    private final Double minRank;

    @JsonProperty
    private final List<DocumentSearchResult> documents;

    public SearchResult(String searchQuery, Double minRank, List<DocumentSearchResult> documents) {
        this.searchQuery = searchQuery;
        this.documents = ImmutableList.copyOf(documents);
        this.minRank = minRank;
    }

    public Double getMinRank() {
        return minRank;
    }

    public List<DocumentSearchResult> getDocuments() {
        return documents;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

}
