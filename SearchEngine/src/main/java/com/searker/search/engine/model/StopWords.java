package com.searker.search.engine.model;

import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Set;

@org.springframework.data.mongodb.core.mapping.Document(collection = "StopWords")
public class StopWords {

    @Field("stop_words")
    private final Set<String> stopWords;

    public StopWords(Set<String> stopWords) {
        this.stopWords = stopWords;
    }

    public Set<String> getStopWords() {
        return stopWords;
    }

}
