package com.sang.system.example.webCollector;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.rocks.BreadthCrawler;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class ZhihuCrawler extends BreadthCrawler {


    /**
     * 构造一个基于RocksDB的爬虫
     * RocksDB文件夹为crawlPath，crawlPath中维护了历史URL等信息
     * 不同任务不要使用相同的crawlPath
     * 两个使用相同crawlPath的爬虫并行爬取会产生错误
     *
     * @param crawlPath RocksDB使用的文件夹
     * @param autoParse 是否根据设置的正则自动探测新URL
     */
    public ZhihuCrawler(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);

        // 设置线程数
        setThreads(50);
        getConf().setTopN(100);

        // 设置是否断点爬取
        setResumable(true);

    }

    /*visit函数定制訪问每一个页面时所需进行的操作*/
    @Override
    public void visit(Page page, CrawlDatums next) {
        String question_regex="^https://www.zhihu.com/question/[0-9]+";
        if(Pattern.matches(question_regex, page.url())){
            System.out.println("正在抽取"+page.url());
            /*抽取标题*/
            String title=page.doc().title();
            System.out.println("标题： " + title);
            /*抽取提问内容*/
            String question=page.doc().select(".QuestionRichText").text();

            System.out.println("问题内容：" + question);

            List<String> answers = page.doc().select(".AnswerItem").eachText();
            answers.forEach(item -> {
                System.out.println("回答内容：");
                System.out.println(item);
            });
        }
    }

    public static void main(String[] args) throws Exception {
        ZhihuCrawler crawler=new ZhihuCrawler("crawl", false);
        crawler.addSeed("https://www.zhihu.com/question/518477330");
        crawler.addRegex("https://www.zhihu.com/.*");
        crawler.start(5);
    }
}
