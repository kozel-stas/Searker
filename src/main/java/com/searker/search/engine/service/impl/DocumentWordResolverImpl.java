package com.searker.search.engine.service.impl;

import com.searker.search.engine.model.Document;
import com.searker.search.engine.service.DocumentWordResolver;
import com.searker.storage.StorageService;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.nio.charset.Charset;

@Service
public class DocumentWordResolverImpl implements DocumentWordResolver {

    private final ResourceLoader resourceLoader = new DefaultResourceLoader();

    @Override
    public String[] resolveKeywords(Document document) throws Exception {
        Resource resource = resourceLoader.getResource(document.getOriginalLocation());
        return StreamUtils.copyToString(resource.getInputStream(), Charset.defaultCharset()).split("\\W+");
    }
}
