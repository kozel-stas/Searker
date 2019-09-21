package com.searker.search.engine.service;

import com.searker.search.engine.model.Document;
import com.searker.search.engine.model.IndexedDocument;

import java.util.Collection;

public interface IndexedDocumentManager {

    Collection<IndexedDocument> getIndexedDocuments();

    void onNewDocumentDiscovered(Document document);

}
