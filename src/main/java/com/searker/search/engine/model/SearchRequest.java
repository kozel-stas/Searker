package com.searker.search.engine.model;

import org.springframework.data.mongodb.core.mapping.Field;

public class SearchRequest {

    @Field("query")
    private String query;

    public SearchRequest(String query) {
        this.query = query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

}
