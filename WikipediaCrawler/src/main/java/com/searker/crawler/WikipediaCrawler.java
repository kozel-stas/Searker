package com.searker.crawler;

import com.searker.DocumentProvider;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.apache.commons.io.IOUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.regex.Pattern;

public class WikipediaCrawler extends WebCrawler {

    private final static Logger LOG = LoggerFactory.getLogger(WikipediaCrawler.class);
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg|png|mp3|mp4|zip|gz))$");

    private final DocumentProvider documentProvider;
    private final HtmlParser htmlParser = new HtmlParser();

    public WikipediaCrawler(DocumentProvider documentProvider) {
        this.documentProvider = documentProvider;
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches() && href.contains("wikipedia.org");
    }

    @Override
    public void visit(Page page) {
        if ("en".equalsIgnoreCase(page.getLanguage()) || "ru".equalsIgnoreCase(page.getLanguage())) {
            if (page.getParseData() instanceof HtmlParseData) {
                HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
                ContentHandler handler = new BodyContentHandler(-1);
                try {
                    htmlParser.parse(IOUtils.toInputStream(htmlParseData.getText()), handler, new Metadata(), new ParseContext());
                } catch (IOException | SAXException | TikaException e) {
                    throw new RuntimeException(e);
                }
                documentProvider.provideNewDocument(page.getWebURL().getURL(), htmlParseData.getTitle(), handler.toString().trim().substring(0, 300));
            }
        } else {
            LOG.info("Page " + page.getWebURL().toString() + "was skipped due to language settings.");
        }
    }

}
