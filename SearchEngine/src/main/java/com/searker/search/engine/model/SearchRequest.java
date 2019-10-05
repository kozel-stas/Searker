package com.searker.search.engine.model;

import org.springframework.data.mongodb.core.mapping.Field;

public class SearchRequest {

    @Field("query")
    private final String query;

    @Field("minRank")
    private final double minRank;

    private double[] searchVector;

    public SearchRequest(String query, Double minRank) {
        this.query = query;
        this.minRank = minRank == null ? 0 : minRank;
    }

    public String getQuery() {
        return query;
    }

    public double getMinRank() {
        return minRank;
    }

    public void setSearchVector(double[] searchVector) {
        this.searchVector = searchVector;
    }

    public double[] getSearchVector() {
        return searchVector;
    }

}
