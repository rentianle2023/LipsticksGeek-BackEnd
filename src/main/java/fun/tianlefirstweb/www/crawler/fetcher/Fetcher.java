package fun.tianlefirstweb.www.crawler.fetcher;

import java.util.List;

/**
 * 爬取信息
 */
public interface Fetcher<T> {

    List<T> fetch();
}
