package fun.tianlefirstweb.www.search.lipstick;

import com.fasterxml.jackson.databind.ObjectMapper;
import fun.tianlefirstweb.www.exception.EntityNotExistException;
import fun.tianlefirstweb.www.exception.UnableToConnectException;
import fun.tianlefirstweb.www.search.SearchRequestDTO;
import fun.tianlefirstweb.www.search.SearchUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static fun.tianlefirstweb.www.search.SearchIndices.LIPSTICK;

@Service
public class LipstickSearchService {

    private final LipstickSearchRepository lipstickSearchRepository;
    private final RestHighLevelClient client;
    private final ObjectMapper mapper;

    public LipstickSearchService(LipstickSearchRepository lipstickSearchRepository,
                                 @Qualifier("elasticsearchClient") RestHighLevelClient client,
                                 ObjectMapper mapper) {
        this.client = client;
        this.mapper = mapper;
        this.lipstickSearchRepository = lipstickSearchRepository;

    }

    public void saveLipstickDocument(LipstickSearch lipstickSearch){
        lipstickSearchRepository.save(lipstickSearch);
    }

    public LipstickSearch searchLipstick(String id){
        return lipstickSearchRepository.findById(id)
            .orElseThrow(() -> new EntityNotExistException(String.format("lipstick with id %s does not exist",id)));
    }

    public List<LipstickSearch> searchLipsticks(SearchRequestDTO requestDTO){
        SearchRequest searchRequest = SearchUtil.buildSearchRequest(LIPSTICK, requestDTO);
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            SearchHit[] searchHits = searchResponse.getHits().getHits();

            List<LipstickSearch> lipsticks = new ArrayList<>();
            for (SearchHit searchHit : searchHits) {
                lipsticks.add(mapper.readValue(searchHit.getSourceAsString(),LipstickSearch.class));
            }

            return lipsticks;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void saveAll(List<LipstickSearch> lipsticks) {
        lipstickSearchRepository.saveAll(lipsticks);
    }

    public void deleteAll() {
        lipstickSearchRepository.deleteAll();
    }
}
