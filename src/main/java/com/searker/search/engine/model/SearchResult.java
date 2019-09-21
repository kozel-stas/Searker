package com.searker.search.engine.model;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class SearchResult {

    private final SearchRequest searchRequest;
    private final List<DocumentSearchResult> documentSearchResults;

    public SearchResult(SearchRequest searchRequest, List<DocumentSearchResult> documentSearchResults) {
        this.searchRequest = searchRequest;
        this.documentSearchResults = ImmutableList.copyOf(documentSearchResults);
    }

    public List<DocumentSearchResult> getDocumentSearchResults() {
        return documentSearchResults;
    }

    public SearchRequest getSearchRequest() {
        return searchRequest;
    }


    public static final class DocumentSearchResult {
        private final double rank;
        private final String documentID;

        public DocumentSearchResult(double rank, String documentID) {
            this.rank = rank;
            this.documentID = documentID;
        }

        public double getRank() {
            return rank;
        }

        public String getDocumentID() {
            return documentID;
        }
    }
}
