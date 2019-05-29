package server.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import server.service.NewsService;
import server.util.GuShiNews;
import server.util.HQNews;
import server.util.NewsVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName NewsVO
 * @PackageName server.controller
 * @Author sheen
 * @Date 2019/5/23
 * @Version 1.0
 * @Description //TODO
 **/
@RestController
@RequestMapping(value = "/api/news")
@CrossOrigin
public class News {

    private final NewsService newsService;

    @Autowired
    public News(NewsService newsService) {
        this.newsService = newsService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public List<NewsVO> getNews() {
        return newsService.getNews();
    }

    @RequestMapping(value = "/hq", method = RequestMethod.GET)
    @ResponseBody
    public List<HQNews> getHqNews() {
        return newsService.getHQNews();
    }

    @RequestMapping(value = "/gushi", method = RequestMethod.GET)
    @ResponseBody
    public List<GuShiNews> getGuShiNews() {
        return newsService.getGuShiNews();
    }




}
