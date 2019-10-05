package com.searker.search.engine.dao;

import com.searker.search.engine.model.Dictionary;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.atomic.AtomicReference;

@Repository
public class DictionaryRepository implements InitializingBean {

    private final MongoOperations mongoOperations;
    private final AtomicReference<String[]> keywords = new AtomicReference<>(new String[0]);

    public DictionaryRepository(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public String[] getDictionary() {
        return keywords.get();
    }

    public void updateDictionary(String[] keywords) {
        mongoOperations.remove(Dictionary.class).findAndRemove();
        Dictionary dictionary = new Dictionary();
        dictionary.setKeywords(keywords);
        mongoOperations.save(dictionary);
        this.keywords.set(keywords);
    }

    @Override
    public void afterPropertiesSet() {
        keywords.set(mongoOperations.query(Dictionary.class).first().orElse(new Dictionary()).getKeywords());
    }

}
