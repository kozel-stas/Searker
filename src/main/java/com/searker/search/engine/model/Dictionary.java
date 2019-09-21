package com.searker.search.engine.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.*;

@Document(collection = "Dictionary")
public class Dictionary {

    @Id
    private String id;

    @Field(value = "keywords")
    private String[] keywords;

    public Dictionary() {
        this.keywords = new String[0];
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
