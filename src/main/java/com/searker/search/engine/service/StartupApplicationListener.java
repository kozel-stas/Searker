package com.searker.search.engine.service;

import com.searker.search.engine.service.impl.IndexedDocumentManagerImpl;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private final IndexedDocumentManagerImpl indexedDocumentManager;

    public StartupApplicationListener(IndexedDocumentManagerImpl indexedDocumentManager) {
        this.indexedDocumentManager = indexedDocumentManager;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        new Thread(indexedDocumentManager).start();
    }
}
