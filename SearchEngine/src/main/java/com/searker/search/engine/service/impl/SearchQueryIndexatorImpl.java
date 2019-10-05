package com.searker.search.engine.service.impl;

import com.searker.search.engine.dao.DictionaryRepository;
import com.searker.search.engine.dao.StopWordsRepository;
import com.searker.search.engine.model.SearchRequest;
import com.searker.search.engine.service.SearchQueryIndexator;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class SearchQueryIndexatorImpl implements SearchQueryIndexator {

    private final DictionaryRepository dictionaryRepository;
    private final StopWordsRepository stopWordsRepository;

    public SearchQueryIndexatorImpl(DictionaryRepository dictionaryRepository, StopWordsRepository stopWordsRepository) {
        this.dictionaryRepository = dictionaryRepository;
        this.stopWordsRepository = stopWordsRepository;
    }

    @Override
    public double[] indexSearchQuery(SearchRequest searchRequest) {
        String[] dictionary = dictionaryRepository.getDictionary();
        Set<String> searchKeywords = new HashSet<>(Arrays.asList(searchRequest.getQuery().toLowerCase().trim().split("((\\s+)|([,.()\\[\\]:-;—/]))")));
        searchKeywords.removeAll(stopWordsRepository.getStopWords());
        searchKeywords.remove("");
        double[] index = new double[dictionary.length];
        for (int i = 0; i < index.length; i++) {
            if (searchKeywords.contains(dictionary[i])) {
                index[i] = 1;
                continue;
            }
            index[i] = 0;
        }
        searchRequest.setSearchVector(index);
        return index;
    }

}
