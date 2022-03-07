package com.sang.system.example.webCollector;

import cn.edu.hfut.dmic.contentextractor.ContentExtractor;
import cn.edu.hfut.dmic.contentextractor.News;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.rocks.BreadthCrawler;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NewsCrawler extends BreadthCrawler {
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * @param crawlPath
	 *            记录爬虫历史的文件夹。可以增量爬取
	 *
	 * @param autoParse
	 *            如果是true，则会自动将符合正则的url加入到待爬取得队列中
	 *
	 */
	public NewsCrawler(String crawlPath, boolean autoParse, String onetype) {
		super(crawlPath, autoParse);

        // 正则规则设置
		this.addRegex("+https://(" + onetype + ").*.sina.com.cn/.*");
		// 不要包含#的
		this.addRegex("-.*#.*");

		// 设置线程数
		setThreads(50);
		// 设置每次迭代中爬取数量的上限
		getConf().setTopN(100);

		// 设置是否为断点爬取
		setResumable(false);

	}

	@Override
	public void visit(Page page, CrawlDatums next) {
		String newsType = getNewsType(page);
	    // 判断是否为新闻页
		if (newsType != null) {

			Map<String, Object> resultMap = new HashMap<>();
			try {
				News mynew = ContentExtractor.getNewsByDoc(page.doc());
				System.err.println(mynew.getTime() + mynew.getTitle() + mynew.getUrl());
				resultMap.put("newsTitle", mynew.getTitle());
				resultMap.put("newsContent", mynew.getContent());
			} catch (Exception e) {
				resultMap.put("newsTitle", page.doc().title());
				resultMap.put("newsContent", page.doc().text());
			}

			resultMap.put("newsURL", page.url());

			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time= sdf.format( new  Date());
			resultMap.put("newsScratchTime", time);
			resultMap.put("newsType", newsType);
			resultMap.put("newsSource", "新浪新闻");

			System.out.println(JSONUtil.toJsonStr(resultMap));
			System.err.println(newsType+"--"+resultMap.get("newsTitle"));
		}
	}

	public String getNewsType(Page page) {
		if (page.matchUrl("https://travel.*sina.com.cn/.*")) {
			return "旅游";
		} else if (page.matchUrl("https://mil.*sina.com.cn/.*")) {
			return "军事";
		} else if (page.matchUrl("https://finance.*sina.com.cn/.*")) {
			return "金融";
		} else if (page.matchUrl("https://sports.*sina.com.cn/.*")) {
			return "体育";
		} else if (page.matchUrl("https://auto.*sina.com.cn/.*")) {
			return "汽车";
		} else if (page.matchUrl("https://tech.*sina.com.cn/.*")) {
			return "科技";
		} else if (page.matchUrl("https://games.*sina.com.cn/.*")) {
			return "游戏";
		} else if (page.matchUrl("https://eladies.*sina.com.cn/.*")) {
			return "女性";
		} else if (page.matchUrl("https://ent.sina.*com.cn/.*")) {
			return "娱乐";
		} else if (page.matchUrl("https://lottery.*sina.com.cn/.*")) {
			return "彩票";
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		for (String onetype : "mil|travel|finance|sports|auto|tech|games|eladies|ent|edu|lottery".split("\\|")) {
			System.err.println(onetype);

			NewsCrawler crawler = new NewsCrawler("crawl", true, onetype);
			// 主入口
			crawler.addSeed("https://news.sina.com.cn/");

			crawler.start(4);
		}
	}

}
