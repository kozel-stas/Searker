package com.searker.rest;

import com.google.common.collect.ImmutableList;
import com.searker.search.engine.dao.DictionaryRepository;
import com.searker.search.engine.model.Dictionary;
import com.searker.search.engine.model.Document;
import com.searker.search.engine.service.DocumentManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class Test {

    private final DocumentManager documentManager;
    private final DictionaryRepository dictionaryRepository;

    public Test(DocumentManager documentManager, DictionaryRepository dictionaryRepository) {
        this.documentManager = documentManager;
        this.dictionaryRepository = dictionaryRepository;
    }

    @RequestMapping("/get")
    @ResponseBody
    public String get(){
        return documentManager.retrieveDocuments().toString();
    }

    @RequestMapping("/insert")
    @ResponseBody
    public String insert(){
        Document document = new Document();
        document.setDate(new Date());
        document.setTitle("Test");
        document.setOriginalLocation("location");
        documentManager.saveDocument(document);
        return "ok";
    }

    @RequestMapping("/dictionaryRep")
    @ResponseBody
    public String dictionary(){
        long d = System.currentTimeMillis();
        Dictionary s = new Dictionary();
        s.setKeywords(ImmutableList.of("wdwdwd"));
        dictionaryRepository.updateDictionary(s);
        return System.currentTimeMillis() - d + "";
    }

    @RequestMapping("/dictionaryRepD")
    @ResponseBody
    public String getD(){
        return dictionaryRepository.getDictionary().getKeywords().toString();
    }

}
