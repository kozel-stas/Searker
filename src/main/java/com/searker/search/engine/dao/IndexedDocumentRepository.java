package com.searker.search.engine.dao;

import com.searker.search.engine.model.IndexedDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IndexedDocumentRepository extends MongoRepository<IndexedDocument, String> {
}
