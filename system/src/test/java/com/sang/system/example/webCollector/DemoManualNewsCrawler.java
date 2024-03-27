//package com.sang.system.example.webCollector;
//
//import cn.edu.hfut.dmic.contentextractor.ContentExtractor;
//import cn.edu.hfut.dmic.contentextractor.News;
//import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
//import cn.edu.hfut.dmic.webcollector.model.Page;
//import cn.edu.hfut.dmic.webcollector.plugin.rocks.BreadthCrawler;
//import lombok.SneakyThrows;
//import org.jsoup.nodes.Document;
//
///**
// * Crawling news from github news
// *
// * @author hu
// */
//public class DemoManualNewsCrawler extends BreadthCrawler {
//    /**
//     * @param crawlPath crawlPath is the path of the directory which maintains
//     *                  information of this crawler
//     * @param autoParse if autoParse is true,BreadthCrawler will auto extract
//     *                  links which match regex rules from pag
//     */
//    public DemoManualNewsCrawler(String crawlPath, boolean autoParse) {
//        super(crawlPath, autoParse);
//        // add 5 start pages and set their type to "list"
//        //"list" is not a reserved word, you can use other string instead
//        this.addSeedAndReturn("https://www.zhihu.com/question/518477330").type("list");
///*        for(int pageIndex = 2; pageIndex <= 5; pageIndex++) {
//            String seedUrl = String.format("https://www.zhihu.com/question/%d", pageIndex);
//            this.addSeed(seedUrl, "list");
//        }*/
//
//        setThreads(50);
//        getConf().setTopN(100);
//
//        // 设置是否断点爬取
//        setResumable(true);
//    }
//
//    @SneakyThrows
//    @Override
//    public void visit(Page page, CrawlDatums next) {
//        String url = page.url();
//
//        if (page.matchType("list")) {
//            /*if type is "list"*/
//            /*detect content page by css selector and mark their types as "content"*/
//            next.add(page.links("h1.lh-condensed>a")).type("content");
//        }else if(page.matchType("content")) {
//            /*if type is "content"*/
//            /*extract title and content of news by css selector*/
//            Document doc = page.doc();
//            News newsByHtml = ContentExtractor.getNewsByDoc(doc);
//            //read title_prefix and content_length_limit from configuration
//
//            System.out.println("URL:\n" + url);
//            System.out.println("title:\n" + newsByHtml.getTitle());
//            System.out.println("time:\n" + newsByHtml.getTime());
//            System.out.println("Content:\n" + newsByHtml.getContent());
//        }
//
//    }
//
//    public static void main(String[] args) throws Exception {
//        DemoManualNewsCrawler crawler = new DemoManualNewsCrawler("crawl", false);
//
//        crawler.getConf().setExecuteInterval(5000);
//
//        crawler.getConf().set("title_prefix","PREFIX_");
//        crawler.getConf().set("content_length_limit", 20);
//
//        /*start crawl with depth of 4*/
//        crawler.start(4);
//    }
//
//}
