package com.searker.rest;

import com.searker.rest.model.DocumentSearchResult;
import com.searker.rest.model.SearchResult;
import com.searker.rest.model.Snippet;
import com.searker.rest.service.URLResolver;
import com.searker.search.engine.model.SearchRequest;
import com.searker.search.engine.service.SearchEngine;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchEngine searchEngine;
    private final URLResolver resolver;

    public SearchController(SearchEngine searchEngine, URLResolver resolver) {
        this.searchEngine = searchEngine;
        this.resolver = resolver;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<SearchResult> document(
            @RequestParam(name = "query") String query,
            @RequestParam(name = "minRank", defaultValue = "0") double minRank
    ) {
        if (minRank > 1 && minRank < 0) {
            return ResponseEntity.badRequest().build();
        }
        com.searker.search.engine.model.SearchResult searchResult = searchEngine.search(new SearchRequest(query, minRank));
        return ResponseEntity.ok(
                new SearchResult(
                        searchResult.getSearchRequest().getQuery(),
                        searchResult.getSearchRequest().getMinRank(),
                        searchResult.getDocumentSearchResults().stream()
                                .map(documentSearchResult -> new DocumentSearchResult(resolver.resolveDocumentLocationURL(documentSearchResult.getDocumentID()), documentSearchResult.getRank(), documentSearchResult.getSnippets().stream().map(snippet -> new Snippet(snippet.getKeyword())).collect(Collectors.toList())))
                                .collect(Collectors.toList())
                )
        );
    }

}
