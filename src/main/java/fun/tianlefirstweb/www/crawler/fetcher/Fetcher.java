package fun.tianlefirstweb.www.crawler.fetcher;

import fun.tianlefirstweb.www.product.lipstick.Lipstick;

import java.util.List;

/**
 * 爬取口红信息
 */
public interface Fetcher {

    /**
     * 爬取口红信息&色号信息
     * 将双方进行bidirectional绑定
     */
    public List<Lipstick> fetch();

    /**
     * 获取当前爬取的品牌名
     */
    public String getBrandName();
}
