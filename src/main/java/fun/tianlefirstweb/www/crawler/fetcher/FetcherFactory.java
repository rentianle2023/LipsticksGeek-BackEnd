package fun.tianlefirstweb.www.crawler.fetcher;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FetcherFactory {

    private final Map<String, Fetcher> fetcherMap;

    public FetcherFactory(List<Fetcher> fetcherList) {
        fetcherMap = new HashMap<>();
        for(var fetcher : fetcherList){
            fetcherMap.put(fetcher.getBrandName(),fetcher);
        }
    }

    public Fetcher getFetcher(String brandName){
        return fetcherMap.get(brandName);
    }
}
