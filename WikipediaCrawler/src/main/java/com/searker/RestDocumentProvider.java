package com.searker;

import com.searker.crawler.WikipediaCrawler;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class RestDocumentProvider implements DocumentProvider {

    private final static Logger LOG = LoggerFactory.getLogger(WikipediaCrawler.class);

    private final HttpClient client = HttpClientBuilder.create().build();
    private final String url = System.getProperty("server.host", "http://localhost:8080") + "/document";

    @Override
    public void provideNewDocument(String location, String title, String desc) {
        LOG.info("Document provided " + location + " " + title);
        try {
            HttpPost request = new HttpPost(url);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", title);
            jsonObject.put("description", desc);
            jsonObject.put("location", location);
            request.setEntity(new StringEntity(jsonObject.toString(), ContentType.APPLICATION_JSON));
            client.execute(request);
        } catch (IOException e) {
            LOG.error("Error was happen!", e);
        }
    }

}
