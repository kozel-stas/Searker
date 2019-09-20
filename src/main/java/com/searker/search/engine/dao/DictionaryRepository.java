package com.searker.search.engine.dao;

import com.searker.search.engine.model.Dictionary;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

@Repository
public class DictionaryRepository {

    private final MongoOperations mongoOperations;

    public DictionaryRepository(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public Dictionary getDictionary() {
        return mongoOperations.query(Dictionary.class).first().orElse(new Dictionary());
    }

    public void updateDictionary(Dictionary dictionary) {
        mongoOperations.remove(Dictionary.class).findAndRemove();
        mongoOperations.save(dictionary);
    }

}
