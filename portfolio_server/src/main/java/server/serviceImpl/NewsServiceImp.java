package server.serviceImpl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import server.service.NewsService;
import server.util.GuShiNews;
import server.util.HQNews;
import server.util.NewsVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName NewsServiceImp
 * @PackageName server.serviceImpl
 * @Author sheen
 * @Date 2019/5/23
 * @Version 1.0
 * @Description //TODO
 **/
@Service
public class NewsServiceImp implements NewsService {
    @Override
    public void flushHQ() {
        saveHtml("http://www.eastmoney.com/","src/main/resources/news/hq/reptile.html");
    }

    @Override
    public void flushNews() {
        saveHtml("http://news.ifeng.com/","src/main/resources/news/content/reptile.html");
    }

    @Scheduled(cron="0/60 * *  * * ? ")
    public void flush(){
        Date date=new Date();
        System.out.print(date+"开始抓取数据 ->");
        flushNews();
        System.out.print("*");
        cacheNewsList=getLocalNewsHtml("src/main/resources/news/content");
        System.out.print("*");
        flushHQ();
        System.out.print("*");
        cacheHQNewsList=getLocalHQNewsHtml("src/main/resources/news/hq");
        System.out.print("*");
        cacheGSNewsList=getLocalGuShiNewsHtml("src/main/resources/news/hq");
        System.out.print("*");
        System.out.println(" !抓取完毕");
    }
    private List<NewsVO>    cacheNewsList   =   null;
    private List<HQNews>    cacheHQNewsList =   null;
    private List<GuShiNews> cacheGSNewsList =   null;
    @Override
    public List<NewsVO> getNews() {
        if(cacheNewsList==null){
            cacheNewsList= getLocalNewsHtml("src/main/resources/news/content");
            return cacheNewsList;
        }else{
            return cacheNewsList;
        }
    }

    @Override
    public List<HQNews> getHQNews() {
        if(cacheHQNewsList==null){
            cacheHQNewsList= getLocalHQNewsHtml("src/main/resources/news/hq");
            return cacheHQNewsList;
        }else{
            return cacheHQNewsList;
        }
    }

    @Override
    public List<GuShiNews> getGuShiNews() {
        if(cacheGSNewsList==null){
            cacheGSNewsList= getLocalGuShiNewsHtml("src/main/resources/news/hq");
            return cacheGSNewsList;
        }else{
            return cacheGSNewsList;
        }
    }

    /**
     *
     * @Title: saveHtml
     * @Description: 将抓取过来的数据保存到本地或者json文件
     * @param url
     * @return void 返回类型
     * @author liangchu
     * @date 2017-12-28 下午12:23:05
     * @throws
     */
    private static void saveHtml(String url,String filePath) {
        try {
            // 这是将首页的信息存入到一个html文件中 为了后面分析html文件里面的信息做铺垫
            File dest = new File(filePath);
            // 接收字节输入流
            InputStream is;
            // 字节输出流
            if(!dest.exists()){
                dest.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(dest);
            URL temp = new URL(url);
            // 这个地方需要加入头部 避免大部分网站拒绝访问
            // 这个地方是容易忽略的地方所以要注意
            URLConnection uc = temp.openConnection();
            // 因为现在很大一部分网站都加入了反爬虫机制 这里加入这个头信息
            uc.addRequestProperty(
                    "User-Agent",
                    "Mozilla/5.0 "
                            + "(iPad; U; CPU OS 4_3_3 like Mac OS X; en-us) "
                            + "AppleWebKit/533.17.9"
                            + " (KHTML, like Gecko) Version/5.0.2 Mobile/8J2 Safari/6533.18.5");
            is = temp.openStream();
            // 为字节输入流加入缓冲
            BufferedInputStream bis = new BufferedInputStream(is);
            // 为字节输出流加入缓冲
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            int length;
            byte[] bytes = new byte[1024 * 20];
            while ((length = bis.read(bytes, 0, bytes.length)) != -1) {
                fos.write(bytes, 0, length);
            }
            bos.close();
            fos.close();
            bis.close();
            is.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
     * 解析本地的html文件获取对应的数据
     */
    private static List<NewsVO> getLocalNewsHtml(String path) {
        // 读取本地的html文件
        File file = new File(path);
        // 获取这个路径下的所有html文件
        File[] files = file.listFiles();
//        List<New> news = new ArrayList<New>();
        HttpServletResponse response = null;
        HttpServletRequest request = null;
        int tmp=1;
        // 循环解析所有的html文件
        List<NewsVO> news=new ArrayList<>();
        try {
            for (int i = 0; i < files.length; i++) {

                // 首先先判断是不是文件
                if (files[i].isFile()) {
                    // 获取文件名
                    String filename = files[i].getName();
                    // 开始解析文件

                    Document doc = Jsoup.parse(files[i], "UTF-8");
                    // 获取所有内容 获取新闻内容
                    Elements contents = doc.getElementsByClass("news-stream-basic-news-list");
                    for (Element element : contents) {
                        for(Element li : element.getElementsByTag("li")){
                            Elements bodys = li.getElementsByTag("img");
                            final Element body = bodys.get(0);
                            final String title = body.attr("alt");
                            final String img = body.attr("src");
                            String source=li.getElementsByClass("news-stream-newsStream-mr10 news-stream-newsStream-text").html();
                            String time=li.getElementsByTag("time").html();
                            String href=li.getElementsByTag("a").attr("href");
                            news.add(new NewsVO(title,"http:"+img,source,time,"http:"+href));
                        }
                    }
                }

            }

            //excelExport(news, response, request);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return news;
    }

    /*
     * 解析本地的html文件获取对应的数据
     */
    private static List<HQNews> getLocalHQNewsHtml(String path) {
        // 读取本地的html文件
        File file = new File(path);
        // 获取这个路径下的所有html文件
        File[] files = file.listFiles();
        int tmp=1;
        // 循环解析所有的html文件
        List<HQNews> news=new ArrayList<>();
        try {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    String filename = files[i].getName();
                    Document doc = Jsoup.parse(files[i], "UTF-8");
                    // 获取所有内容 获取新闻内容
                    Elements contents = doc.getElementsByClass("hq-news-data");
                    for (Element element : contents) {
                        final String name = element.getElementsByClass("item_name").html();
                        final Elements stockdown = element.getElementsByClass("stockdown");

                        String nowPrice = stockdown.size()>0?stockdown.get(0).html():"无数据";
                        String changedPrice = stockdown.size()>1?stockdown.get(1).html():"无数据";
                        String percent = stockdown.size()>2?stockdown.get(2).html():"无数据";
                        news.add(new HQNews(name,nowPrice,changedPrice,percent));
                    }
                }

            }

            //excelExport(news, response, request);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return news;
    }
    private static List<GuShiNews> getLocalGuShiNewsHtml(String path) {
        // 读取本地的html文件
        File file = new File(path);
        // 获取这个路径下的所有html文件
        File[] files = file.listFiles();
        int tmp=1;
        // 循环解析所有的html文件
        List<GuShiNews> news=new ArrayList<>();
        try {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    String filename = files[i].getName();
                    Document doc = Jsoup.parse(files[i], "UTF-8");
                    // 获取所有内容 获取新闻内容
                    Elements contents = doc.getElementsByClass("newdatatable hangye-data");
                    for (Element tb : contents) {

                        for(Element tr:tb.getElementsByTag("tr")){
                            final Elements elementsByClass = tr.getElementsByClass("gu-data-type");
                            if(elementsByClass.size()>0){
                                String block=elementsByClass.html();
                                String block_stockup=tr.getElementsByClass("new-prive data-comm stockup").html();
                                String comm=tr.getElementsByClass("lingzhang-text").html();
                                String comm_stockup=tr.getElementsByClass("zhangdie data-lzg-comm stockup").html();
                                news.add(new GuShiNews(block,block_stockup,comm,comm_stockup));
                            }
                        }
//                        final String name = element.getElementsByClass("gu-data-type").html();
//                        final Elements stockdown = element.getElementsByClass("stockdown");
//
//                        String nowPrice = stockdown.size()>0?stockdown.get(0).html():"无数据";
//                        String changedPrice = stockdown.size()>1?stockdown.get(1).html():"无数据";
//                        String percent = stockdown.size()>2?stockdown.get(2).html():"无数据";
//                        for(Element element2 : element.getElementsByClass("item_name")){
//
//                            System.out.println(element2.html());
//                        }
//
                    }
                }

            }

            //excelExport(news, response, request);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return news;
    }
}
