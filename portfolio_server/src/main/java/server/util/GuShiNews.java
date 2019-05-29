package server.util;

/**
 * @ClassName HQNews
 * @PackageName server.util
 * @Author sheen
 * @Date 2019/5/23
 * @Version 1.0
 * @Description //TODO
 **/
public class GuShiNews {
    final String block;
    final String block_stockup ;
    final String comm ;
    final String comm_stockup ;

    public GuShiNews(String block, String block_stockup, String comm, String comm_stockup) {
        this.block = block;
        this.block_stockup = block_stockup;
        this.comm = comm;
        this.comm_stockup = comm_stockup;
    }

    public String getBlock() {
        return block;
    }

    public String getBlock_stockup() {
        return block_stockup;
    }

    public String getComm() {
        return comm;
    }

    public String getComm_stockup() {
        return comm_stockup;
    }
}
