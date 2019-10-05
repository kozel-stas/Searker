package com.searker.rest;

import com.searker.rest.model.DocumentSearchResult;
import com.searker.rest.model.SearchResult;
import com.searker.rest.model.Snippet;
import com.searker.rest.service.URLResolver;
import com.searker.search.engine.service.MetricManager;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/metrics")
public class MetricController {

    private final MetricManager metricManager;
    private final URLResolver urlResolver;

    public MetricController(MetricManager metricManager, URLResolver urlResolver) {
        this.metricManager = metricManager;
        this.urlResolver = urlResolver;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<SearchResult>> metrics() {
        return ResponseEntity.ok(metricManager.retrieveMetrics().stream()
                .map(searchResult -> new SearchResult(searchResult.getSearchRequest().getQuery(), searchResult.getSearchRequest().getMinRank(), searchResult.getDocumentSearchResults().stream()
                        .map(documentSearchResult -> new DocumentSearchResult(urlResolver.resolveDocumentLocationURL(documentSearchResult.getDocumentID()), documentSearchResult.getRank(), documentSearchResult.getSnippets().stream().map(snippet -> new Snippet(snippet.getKeyword()))
                                .collect(Collectors.toList()))).collect(Collectors.toList()))).collect(Collectors.toList()));
    }

}
