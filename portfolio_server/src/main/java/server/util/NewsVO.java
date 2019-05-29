package server.util;

/**
 * @ClassName NewsVO
 * @PackageName server.util
 * @Author sheen
 * @Date 2019/5/23
 * @Version 1.0
 * @Description //TODO
 **/
public class NewsVO {
    String title;
    String img;
    String source;
    String time;
    String href;

    public NewsVO(String title, String img, String source, String time,String href) {
        this.title = title;
        this.img = img;
        this.source = source;
        this.time = time;
        this.href=href;
    }

    public String getTitle() {
        return title;
    }

    public String getHref() {
        return href;
    }

    public String getImg() {
        return img;
    }

    public String getSource() {
        return source;
    }

    public String getTime() {
        return time;
    }
}
