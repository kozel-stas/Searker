package com.searker.search.engine.service.impl;

import com.searker.search.engine.dao.DictionaryRepository;
import com.searker.search.engine.dao.DocumentRepository;
import com.searker.search.engine.dao.IndexedDocumentRepository;
import com.searker.search.engine.model.Document;
import com.searker.search.engine.model.IndexedDocument;
import com.searker.search.engine.service.DocumentWordResolver;
import com.searker.search.engine.service.IndexedDocumentManager;
import com.searker.search.engine.util.CollectionUtil;
import com.searker.search.engine.util.VectorUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class IndexedDocumentManagerImpl implements IndexedDocumentManager, Runnable {

    private static final Logger LOG = LogManager.getLogger(IndexedDocumentManagerImpl.class);

    private final DocumentRepository documentRepository;
    private final DictionaryRepository dictionaryRepository;
    private final IndexedDocumentRepository indexedDocumentRepository;
    private final DocumentWordResolver documentWordResolver;
    private volatile Collection<IndexedDocument> cachedIndexedDocuments = new ArrayList<>();

    private final BlockingQueue<Document> documentsForIndexation = new LinkedBlockingQueue<>();

    public IndexedDocumentManagerImpl(
            DocumentRepository documentRepository,
            DictionaryRepository dictionaryRepository,
            IndexedDocumentRepository indexedDocumentRepository,
            DocumentWordResolver documentWordResolver
    ) {
        this.documentRepository = documentRepository;
        this.dictionaryRepository = dictionaryRepository;
        this.indexedDocumentRepository = indexedDocumentRepository;
        this.documentWordResolver = documentWordResolver;
    }

    @Override
    public Collection<IndexedDocument> getIndexedDocuments() {
        return Collections.unmodifiableCollection(cachedIndexedDocuments);
    }

    @Override
    public void onNewDocumentDiscovered(Document document) {
        documentsForIndexation.add(document);
    }

    private Collection<IndexedDocument> indexDocuments(List<Document> documents, @Nullable Collection<IndexedDocument> previousIndexation, String[] dictionary) {
        Map<String, Document> documentsMap = documents.stream().collect(Collectors.toMap(Document::getId, document -> document));
        Map<String, IndexedDocument> indexedDocumentMap = previousIndexation == null ? new HashMap<>() : previousIndexation.stream().collect(Collectors.toMap(IndexedDocument::getId, document -> document));
        CollectionUtil.reduce(documentsMap, indexedDocumentMap);

        Map<String, String[]> wordDocuments = resolveDocumentKeywords(documentsMap);

        Set<String> newKeywords = wordDocuments.values().stream().flatMap((Function<String[], Stream<String>>) Arrays::stream).collect(Collectors.toSet());
        newKeywords.removeAll(Arrays.asList(dictionary));

        String[] keywords = CollectionUtil.union(dictionary, newKeywords.toArray(new String[0]));
        int[] keywordNumber = new int[keywords.length];
        for (Document document : documentsMap.values()) {
            IndexedDocument indexedDocument = new IndexedDocument();
            indexedDocument.setId(document.getId());
            Map<String, Integer> wordsNumber = new HashMap<>();
            for (String string : wordDocuments.get(document.getId())) {
                wordsNumber.computeIfPresent(string, (s, integer) -> integer + 1);
                wordsNumber.putIfAbsent(string, 1);
            }
            for (int i = 0; i < keywords.length; i++) {
                keywordNumber[i] = wordsNumber.getOrDefault(keywords[i], 0);
            }
            indexedDocument.setKeywordNumber(Arrays.copyOf(keywordNumber, keywordNumber.length));
            indexedDocumentMap.put(document.getId(), indexedDocument);
        }
        Arrays.fill(keywordNumber, 0);
        for (IndexedDocument indexedDocument : indexedDocumentMap.values()) {
            keywordNumber = VectorUtil.sum(keywordNumber, indexedDocument.getKeywordNumber(), 1);
        }
        for (IndexedDocument indexedDocument : indexedDocumentMap.values()) {
            double[] weightVector = new double[indexedDocument.getKeywordNumber().length];
            double countWeight = 0;
            for (int i = 0; i < indexedDocument.getKeywordNumber().length; i++) {
                countWeight += ((double) indexedDocument.getKeywordNumber()[i] * Math.log(((double) (indexedDocumentMap.size() + 1) / keywordNumber[i])));
            }
            for (int i = 0; i < indexedDocument.getKeywordNumber().length; i++) {
                weightVector[i] = ((double) indexedDocument.getKeywordNumber()[i] * Math.log(((double) (indexedDocumentMap.size() + 1) / keywordNumber[i])))
                        / (countWeight);
            }
            indexedDocument.setWeightVector(weightVector);
        }
        dictionaryRepository.updateDictionary(keywords);
        indexedDocumentRepository.saveAll(indexedDocumentMap.values());
        return indexedDocumentMap.values();
    }

    private Map<String, String[]> resolveDocumentKeywords(Map<String, Document> documentsMap) {
        Map<String, String[]> wordDocuments = new HashMap<>();
        Iterator<Document> iterator = documentsMap.values().iterator();
        while (iterator.hasNext()) {
            Document document = iterator.next();
            try {
                wordDocuments.put(document.getId(), documentWordResolver.resolveKeywords(document));
            } catch (Exception e) {
                LOG.error(e);
                iterator.remove();
                documentRepository.delete(document);
            }
        }
        return wordDocuments;
    }


    private static final long INDEX_THRESHOLD = TimeUnit.SECONDS.toMillis(10);

    @Override
    public void run() {
        fullReindexIfNeeded();
        LOG.info("Started.");
        List<Document> documents = new ArrayList<>();
        long firstDocumentWaitTime = Long.MAX_VALUE;
        while (true) {
            try {
                Document document = documentsForIndexation.poll(INDEX_THRESHOLD, TimeUnit.MILLISECONDS);
                if (document != null) {
                    documents.add(document);
                }
                if (documents.size() == 1 && document != null) {
                    firstDocumentWaitTime = System.currentTimeMillis();
                }
                if (firstDocumentWaitTime + INDEX_THRESHOLD < System.currentTimeMillis() && documents.size() != 0) {
                    firstDocumentWaitTime = Long.MAX_VALUE;
                    List<Document> documentsForIndexation = new ArrayList<>(documents);
                    documents.clear();
                    cachedIndexedDocuments = indexDocuments(documentsForIndexation, cachedIndexedDocuments, dictionaryRepository.getDictionary());
                }
            } catch (RuntimeException e) {
                LOG.error(e);
                documents.clear();
                firstDocumentWaitTime = Long.MAX_VALUE;
                fullReindexIfNeeded();
            } catch (InterruptedException e) {
                LOG.error(e);
                return;
            }
        }
    }

    private void fullReindexIfNeeded() {
        if (indexedDocumentRepository.count() == documentRepository.count()) {
            cachedIndexedDocuments = indexedDocumentRepository.findAll();
        } else {
            cachedIndexedDocuments = indexDocuments(documentRepository.findAll(), indexedDocumentRepository.findAll(), dictionaryRepository.getDictionary());
        }
    }

}
