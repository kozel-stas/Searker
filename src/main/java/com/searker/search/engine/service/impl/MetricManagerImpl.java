package com.searker.search.engine.service.impl;

import com.searker.search.engine.dao.SearchResultRepository;
import com.searker.search.engine.model.SearchResult;
import com.searker.search.engine.service.MetricManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class MetricManagerImpl implements MetricManager, Runnable {

    private final static Logger LOG = LogManager.getLogger(MetricManagerImpl.class);

    private final SearchResultRepository searchResultRepository;
    private final BlockingQueue<SearchResult> searchResults = new LinkedBlockingQueue<>();


    public MetricManagerImpl(SearchResultRepository searchResultRepository) {
        this.searchResultRepository = searchResultRepository;
    }

    @Override
    public SearchResult register(SearchResult searchResult) {
        searchResults.add(searchResult);
        return searchResult;
    }

    @Override
    public List<SearchResult> retrieveMetrics() {
        return searchResultRepository.findAll();
    }

    @Override
    public void run() {
        while (true) {
            try {
                SearchResult searchResult = searchResults.take();
                searchResultRepository.save(searchResult);
            } catch (InterruptedException e) {
                LOG.error(e);
                return;
            } catch (RuntimeException e) {
                LOG.error(e);
            }
        }
    }
}
