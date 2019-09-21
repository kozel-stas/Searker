package com.searker.search.engine.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "IndexedDocuments")
public class IndexedDocument {

    @Id
    private String id;

    @Field(value = "weight_vector")
    private double[] weightVector;

    @Field(value = "keyword_number")
    private int[] keywordNumber;

    public void setWeightVector(double[] weightVector) {
        this.weightVector = weightVector;
    }

    public double[] getWeightVector() {
        return weightVector;
    }

    public void setKeywordNumber(int[] keywordNumber) {
        this.keywordNumber = keywordNumber;
    }

    public int[] getKeywordNumber() {
        return keywordNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
