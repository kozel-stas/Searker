package com.searker.search.engine.model;

public class Snippet {

    private final String keyword;
    private final double weight;

    public Snippet(String keyword, double weight) {
        this.keyword = keyword;
        this.weight = weight;
    }

    public String getKeyword() {
        return keyword;
    }

    public double getWeight() {
        return weight;
    }

}
