package com.searker.search.engine.model;

public class SearchRequest {

    private final String query;

    public SearchRequest(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

}
