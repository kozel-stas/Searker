package com.searker.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DocumentSearchResult {

    @JsonProperty
    private final String document;
    @JsonProperty
    private final double rank;

    public DocumentSearchResult(String document, double rank) {
        this.document = document;
        this.rank = rank;
    }

    public double getRank() {
        return rank;
    }

    public String getDocument() {
        return document;
    }

}
