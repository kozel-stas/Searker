package com.searker.search.engine.service.impl;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private final IndexedDocumentManagerImpl indexedDocumentManager;
    private final MetricManagerImpl metricManager;

    public StartupApplicationListener(IndexedDocumentManagerImpl indexedDocumentManager, MetricManagerImpl metricManager) {
        this.indexedDocumentManager = indexedDocumentManager;
        this.metricManager = metricManager;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        new Thread(indexedDocumentManager).start();
        new Thread(metricManager).start();
    }
}
