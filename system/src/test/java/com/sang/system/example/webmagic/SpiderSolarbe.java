package com.sang.system.example.webmagic;

import cn.hutool.core.collection.CollUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.sql.SQLException;
import java.util.List;

public class SpiderSolarbe implements PageProcessor {


    //设置网站相关配置
    //重试次数和抓取间隔
    private Site site = Site.me().setRetryTimes(5).setSleepTime(1000).setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");

    @Override
    public void process(Page page) {

        List<String> all = page.getHtml().xpath("//div[@class=newslistItem]/html()").all();

        if (CollUtil.isNotEmpty(all)) {
            for (String s : all) {
                Html html = new Html(s);
                String s1 = html.xpath("//a[@class=newsTitle   ]/text()").get();
                String s2 = html.links().regex("(https://news.solarbe.com/\\w+/\\w+/\\w+.html)").get();
                String s3 = html.xpath("//p[@class=desc]/text()").get();
                String s4 = html.xpath("//span[@class=timestamp]/text()").get();

                String substring = s2.substring(s2.lastIndexOf("/")+1, s2.lastIndexOf("."));

                page.putField("list-" + substring, s1.concat(s2).concat(s3).concat(s4));
            }
            page.addTargetRequests(page.getHtml().links().regex("(https://news.solarbe.com/202311/\\w+/\\w+.html)").all());
        } else {
            page.putField("title",page.getHtml().xpath("//h1[@class='mainbody-body-title']/text()").get());
            if (page.getResultItems().get("title") == null) {
               page.setSkip(true);
            }
            page.putField("detail",page.getHtml().xpath("//div[@class='mainbody-body-con']/html()").get());
        }

    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) throws SQLException {


        Spider.create(new SpiderSolarbe()).addUrl("https://news.solarbe.com/guoji/")
                //输出到控制台
                .addPipeline(new JsonFilePipeline("D:/webmagic/"))
                //开启5个线程抓取
                .thread(5)
                //启动爬虫
                .run();

    }
}
