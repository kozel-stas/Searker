package com.searker.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.LinkedList;

@Document(collection = "dictionary")
public class Dictionary {

    @Field(value = "keywords")
    private LinkedList<String> keywords;

    public void setKeywords(LinkedList<String> keywords) {
        this.keywords = keywords;
    }

    public LinkedList<String> getKeywords() {
        return keywords;
    }

}
