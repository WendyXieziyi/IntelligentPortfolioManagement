package server.util;

/**
 * @ClassName HQNews
 * @PackageName server.util
 * @Author sheen
 * @Date 2019/5/23
 * @Version 1.0
 * @Description //TODO
 **/
public class HQNews {
    final String name;
    final String nowPrice ;
    final String changedPrice ;
    final String percent ;

    public HQNews(String name, String nowPrice, String changedPrice, String percent) {
        this.name = name;
        this.nowPrice = nowPrice;
        this.changedPrice = changedPrice;
        this.percent = percent;
    }

    public String getName() {
        return name;
    }

    public String getNowPrice() {
        return nowPrice;
    }

    public String getChangedPrice() {
        return changedPrice;
    }

    public String getPercent() {
        return percent;
    }
}
