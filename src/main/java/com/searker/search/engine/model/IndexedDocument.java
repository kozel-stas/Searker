package com.searker.search.engine.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "indexed_documents")
public class IndexedDocument {

    @Id
    private Long id;

    @Field(value = "weight_vector")
    private int[] weightVector;

    public void setId(Long id) {
        this.id = id;
    }

    public void setWeightVector(int[] weightVector) {
        this.weightVector = weightVector;
    }

    public int[] getWeightVector() {
        return weightVector;
    }

    public Long getId() {
        return id;
    }

}
