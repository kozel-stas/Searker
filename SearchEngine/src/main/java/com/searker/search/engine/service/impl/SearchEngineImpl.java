package com.searker.search.engine.service.impl;

import com.searker.search.engine.model.IndexedDocument;
import com.searker.search.engine.model.SearchRequest;
import com.searker.search.engine.model.SearchResult;
import com.searker.search.engine.service.*;
import com.searker.search.engine.util.VectorUtil;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Service
public class SearchEngineImpl implements SearchEngine {

    private final IndexedDocumentManager indexedDocumentManager;
    private final SearchQueryIndexator searchQueryIndexator;
    private final MetricManager metricManager;
    private final SnippetResolver snippetResolver;

    public SearchEngineImpl(IndexedDocumentManager indexedDocumentManager, SearchQueryIndexator searchQueryIndexator, MetricManager metricManager, SnippetResolver snippetResolver) {
        this.indexedDocumentManager = indexedDocumentManager;
        this.searchQueryIndexator = searchQueryIndexator;
        this.metricManager = metricManager;
        this.snippetResolver = snippetResolver;
    }

    @Override
    public SearchResult search(SearchRequest searchRequest) {
        Collection<IndexedDocument> documents = indexedDocumentManager.getIndexedDocuments();
        double[] searchIndex = searchQueryIndexator.indexSearchQuery(searchRequest);
        List<SearchResult.DocumentSearchResult> documentSearchResults = new LinkedList<>();
        for (IndexedDocument indexedDocument : documents) {
            double scalarMultiplication = VectorUtil.scalarMultiplication(indexedDocument.getWeightVector(), searchIndex, 0);
            double rank = scalarMultiplication == 0.0 ? 0 : scalarMultiplication /
                    (VectorUtil.euclideanNorm(indexedDocument.getWeightVector()) * VectorUtil.euclideanNorm(searchIndex));
            if (rank >= searchRequest.getMinRank()) {
                documentSearchResults.add(
                        new SearchResult.DocumentSearchResult(
                                rank,
                                indexedDocument.getId(),
                                snippetResolver.resolveSnippets(searchRequest, indexedDocument)
                        )
                );
            }
        }
        documentSearchResults.sort((o1, o2) -> Double.compare(o2.getRank(), o1.getRank()));
        return metricManager.register(new SearchResult(searchRequest, documentSearchResults));
    }

}
