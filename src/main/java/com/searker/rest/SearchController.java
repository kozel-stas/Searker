package com.searker.rest;

import com.searker.rest.model.DocumentSearchResult;
import com.searker.rest.model.SearchResult;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @RequestMapping(
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<SearchResult> document(@RequestParam(name = "query") String query) {
        List<DocumentSearchResult> searchResults = new ArrayList<>();
        searchResults.add(new DocumentSearchResult("/document/5d84ffef5807e90d50df47b5", 60));
        searchResults.add(new DocumentSearchResult("/document/5d83ddeb5807e91e146ce12a", 55));
        SearchResult searchResult = new SearchResult(query, searchResults);
        return ResponseEntity.ok(searchResult);
    }

}
