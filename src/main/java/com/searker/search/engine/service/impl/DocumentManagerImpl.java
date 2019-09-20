package com.searker.search.engine.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.searker.search.engine.dao.DocumentRepository;
import com.searker.search.engine.model.Document;
import com.searker.search.engine.service.DocumentManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class DocumentManagerImpl implements DocumentManager {

    private final static Logger LOG = LogManager.getLogger(DocumentManagerImpl.class);

    private final DocumentRepository documentRepository;
    private final LoadingCache<String, Document> cache = CacheBuilder
            .newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .recordStats()
            .build(new CacheLoader<String, Document>() {
                @Override
                public Document load(@Nonnull String id) {
                    Optional<Document> document = documentRepository.findById(id);
                    if (document.isPresent()) {
                        return document.get();
                    }
                    throw new InvalidCacheLoadException("Persistent storage isn't contain data for key " + id);
                }
            });

    public DocumentManagerImpl(@Qualifier(value = "documentRepository") DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public Document saveDocument(Document document) {
        return documentRepository.save(document);
    }

    public Document retrieveDocument(String id) {
        try {
            return cache.get(id);
        } catch (ExecutionException e) {
            LOG.error(e);
            return null;
        }
    }

    public List<Document> retrieveDocuments() {
        return documentRepository.findAll();
    }

}
