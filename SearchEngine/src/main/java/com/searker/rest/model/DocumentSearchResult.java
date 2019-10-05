package com.searker.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DocumentSearchResult {

    @JsonProperty
    private final String document;
    @JsonProperty
    private final double rank;
    @JsonProperty
    private final List<Snippet> snippets;

    public DocumentSearchResult(String document, double rank, List<Snippet> snippets) {
        this.document = document;
        this.rank = rank;
        this.snippets = snippets;
    }

    public double getRank() {
        return rank;
    }

    public String getDocument() {
        return document;
    }

    public List<Snippet> getSnippets() {
        return snippets;
    }
}
