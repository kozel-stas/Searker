package com.searker.search.engine.service.impl;

import com.searker.search.engine.dao.DictionaryRepository;
import com.searker.search.engine.model.IndexedDocument;
import com.searker.search.engine.model.SearchRequest;
import com.searker.search.engine.model.Snippet;
import com.searker.search.engine.service.SnippetResolver;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class SnippetResolverImpl implements SnippetResolver {

    private final DictionaryRepository dictionaryRepository;

    public SnippetResolverImpl(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }

    @Override
    public List<Snippet> resolveSnippets(SearchRequest searchRequest, IndexedDocument indexedDocument) {
        List<Snippet> snippets = new LinkedList<>();
        String[] dictionary = dictionaryRepository.getDictionary();
        double[] indexVector = searchRequest.getSearchVector();
        for (int i = 0; i < Math.min(indexVector.length, indexedDocument.getWeightVector().length); i++) {
            if (indexVector[i] == 1 && indexedDocument.getWeightVector()[i] > 0) {
                snippets.add(new Snippet(dictionary[i], indexedDocument.getWeightVector()[i]));
            }
        }
        snippets.sort((o1, o2) -> Double.compare(o2.getWeight(), o1.getWeight()));
        return snippets;
    }

}
