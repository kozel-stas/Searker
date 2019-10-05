package com.searker;

import com.searker.crawler.WikipediaCrawler;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

import java.util.Objects;

public class Application {

    public static void main(String[] args) throws Exception {
        System.getProperties().load(Objects.requireNonNull(Application.class.getClassLoader().getResourceAsStream("application.properties")));

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(System.getProperty("crawler.folder"));
        config.setIncludeHttpsPages(true);
        config.setShutdownOnEmptyQueue(true);
        config.setPolitenessDelay(Integer.parseInt(System.getProperty("crawler.pause")));

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        robotstxtConfig.setEnabled(false);
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        controller.addSeed("https://en.wikipedia.org/");
        controller.addSeed("https://ru.wikipedia.org/");

        // The factory which creates instances of crawlers.
        CrawlController.WebCrawlerFactory<WebCrawler> factory = () -> new WikipediaCrawler(new RestDocumentProvider());

        // Start the crawl. This is a blocking operation, meaning that your code
        // will reach the line after this only when crawling is finished.
        controller.start(factory, Integer.valueOf(System.getProperty("crawler.number")));
    }

}
