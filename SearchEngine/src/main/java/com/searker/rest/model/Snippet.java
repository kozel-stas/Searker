package com.searker.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Snippet {

    @JsonProperty
    private final String keyword;

    public Snippet(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

}
