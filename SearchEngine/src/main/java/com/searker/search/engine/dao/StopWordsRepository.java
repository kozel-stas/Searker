package com.searker.search.engine.dao;

import com.searker.search.engine.model.StopWords;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Repository
public class StopWordsRepository implements InitializingBean {

    private final MongoOperations mongoOperations;
    private final AtomicReference<StopWords> stopWords = new AtomicReference<>(new StopWords(Collections.emptySet()));

    public StopWordsRepository(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public void afterPropertiesSet() {
        stopWords.set(mongoOperations.query(StopWords.class).first().orElse(new StopWords(Collections.emptySet())));
    }

    public Set<String> getStopWords() {
        return Collections.unmodifiableSet(stopWords.get().getStopWords());
    }

}
