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
    private HashSet<String> keywords;

    public Dictionary() {
        this.keywords = new HashSet<>();
    }

    public void setKeywords(Collection<String> keywords) {
        this.keywords.addAll(keywords);
    }

    public Set<String> getKeywords() {
        return Collections.unmodifiableSet(keywords);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
