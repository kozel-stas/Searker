package com.searker.search.engine.service.impl;

import com.searker.search.engine.dao.StopWordsRepository;
import com.searker.search.engine.model.Document;
import com.searker.search.engine.service.DocumentWordResolver;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.xml.sax.ContentHandler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class DocumentWordResolverImpl implements DocumentWordResolver {

    private final ResourceLoader resourceLoader = new DefaultResourceLoader();
    private final HtmlParser htmlParser = new HtmlParser();
    private final StopWordsRepository stopWordsRepository;

    public DocumentWordResolverImpl(StopWordsRepository stopWordsRepository) {
        this.stopWordsRepository = stopWordsRepository;
    }

    @Override
    public String[] resolveKeywords(Document document) throws Exception {
        Resource resource = resourceLoader.getResource(document.getOriginalLocation());
        ContentHandler handler = new BodyContentHandler(-1);
        htmlParser.parse(resource.getInputStream(), handler, new Metadata(), new ParseContext());
        Set<String> keywords = new HashSet<>(Arrays.asList(handler.toString().toLowerCase().trim().split("((\\s+)|([,.()\\[\\]:-;—/]))")));
        keywords.removeAll(stopWordsRepository.getStopWords());
        keywords.remove("");
        return keywords.toArray(new String[0]);
    }
}
