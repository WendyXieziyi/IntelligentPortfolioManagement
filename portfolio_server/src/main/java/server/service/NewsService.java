package server.service;

import server.controller.News;
import server.util.GuShiNews;
import server.util.HQNews;
import server.util.NewsVO;

import java.util.List;

/**
 * @ClassName NewsService
 * @PackageName server.service
 * @Author sheen
 * @Date 2019/5/23
 * @Version 1.0
 * @Description //TODO
 **/
public interface NewsService {
    public void flushHQ();
    public void flushNews();
    public List<NewsVO> getNews();
    public List<HQNews> getHQNews();
    public List<GuShiNews> getGuShiNews();
}
