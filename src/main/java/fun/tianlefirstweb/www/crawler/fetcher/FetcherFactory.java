package fun.tianlefirstweb.www.crawler.fetcher;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FetcherFactory {

    private final Map<String, LipstickFetcher> fetcherMap;

    public FetcherFactory(List<LipstickFetcher> fetcherList) {
        fetcherMap = new HashMap<>();
        for(var fetcher : fetcherList){
            fetcherMap.put(fetcher.getBrandName(),fetcher);
        }
    }

    public LipstickFetcher getFetcher(String brandName){
        return fetcherMap.get(brandName);
    }
}
