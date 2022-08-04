package fun.tianlefirstweb.www.search.lipstickColor;

import com.fasterxml.jackson.databind.ObjectMapper;
import fun.tianlefirstweb.www.search.SearchRequestDTO;
import fun.tianlefirstweb.www.search.SearchUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static fun.tianlefirstweb.www.search.SearchIndices.LIPSTICK_COLOR;

@Service
public class LipstickColorSearchService {

    private final LipstickColorSearchRepository lipstickColorSearchRepository;
    private final RestHighLevelClient client;
    private final ObjectMapper mapper;

    public LipstickColorSearchService(LipstickColorSearchRepository lipstickColorSearchRepository,
                                      @Qualifier("elasticsearchClient") RestHighLevelClient client,
                                      ObjectMapper mapper) {
        this.lipstickColorSearchRepository = lipstickColorSearchRepository;
        this.client = client;
        this.mapper = mapper;
    }

    public void save(LipstickColorSearch lipstickColorSearch){
        lipstickColorSearchRepository.save(lipstickColorSearch);
    }

    public void saveAll(List<LipstickColorSearch> colors){
        lipstickColorSearchRepository.saveAll(colors);
    }


    public List<LipstickColorSearch> search(SearchRequestDTO requestDTO){
        return getHits(SearchUtil.buildSearchRequest(LIPSTICK_COLOR, requestDTO));
    }

    public List<LipstickColorSearch> search(String hexColor){
        return getHits(SearchUtil.buildSearchRequest(LIPSTICK_COLOR, hexColor));
    }

    private List<LipstickColorSearch> getHits(SearchRequest searchRequest){
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            SearchHit[] searchHits = searchResponse.getHits().getHits();

            List<LipstickColorSearch> colors = new ArrayList<>();
            for (SearchHit searchHit : searchHits) {
                colors.add(mapper.readValue(searchHit.getSourceAsString(),LipstickColorSearch.class));
            }
            System.out.println(searchResponse.getHits().getTotalHits());
            return colors;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteAll() {
        lipstickColorSearchRepository.deleteAll();
    }
}
