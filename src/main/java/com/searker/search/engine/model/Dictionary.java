package com.searker.search.engine.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.LinkedList;
import java.util.List;

@Document(collection = "Dictionary")
public class Dictionary {

    @Id
    private String id;

    @Field(value = "keywords")
    private LinkedList<String> keywords;

    public Dictionary() {
        this.keywords = new LinkedList<>();
    }

    public void setKeywords(List<String> keywords) {
        this.keywords.addAll(keywords);
    }

    public LinkedList<String> getKeywords() {
        return keywords;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
