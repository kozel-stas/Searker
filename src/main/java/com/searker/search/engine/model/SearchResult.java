package com.searker.search.engine.model;

import com.google.common.collect.ImmutableList;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document("SearchResult")
public class SearchResult {

    @Id
    private String id;

    @Field("requests")
    private SearchRequest searchRequest;
    @Field("results")
    private List<DocumentSearchResult> documentSearchResults;

    public SearchResult(SearchRequest searchRequest, List<DocumentSearchResult> documentSearchResults) {
        this.searchRequest = searchRequest;
        this.documentSearchResults = ImmutableList.copyOf(documentSearchResults);
    }

    public void setDocumentSearchResults(List<DocumentSearchResult> documentSearchResults) {
        this.documentSearchResults = documentSearchResults;
    }

    public void setSearchRequest(SearchRequest searchRequest) {
        this.searchRequest = searchRequest;
    }

    public List<DocumentSearchResult> getDocumentSearchResults() {
        return documentSearchResults;
    }

    public SearchRequest getSearchRequest() {
        return searchRequest;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static final class DocumentSearchResult {
        @Field("rank")
        private double rank;
        @Field("documentID")
        private String documentID;

        public DocumentSearchResult(double rank, String documentID) {
            this.rank = rank;
            this.documentID = documentID;
        }

        public void setDocumentID(String documentID) {
            this.documentID = documentID;
        }

        public void setRank(double rank) {
            this.rank = rank;
        }

        public double getRank() {
            return rank;
        }

        public String getDocumentID() {
            return documentID;
        }
    }
}
