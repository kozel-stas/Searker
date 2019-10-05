package com.searker.search.engine.dao;

import com.searker.search.engine.model.SearchResult;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SearchResultRepository extends MongoRepository<SearchResult, String> {
}
