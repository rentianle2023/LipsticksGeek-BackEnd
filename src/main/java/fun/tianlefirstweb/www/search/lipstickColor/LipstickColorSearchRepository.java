package fun.tianlefirstweb.www.search.lipstickColor;

import fun.tianlefirstweb.www.search.lipstick.LipstickSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface LipstickColorSearchRepository extends ElasticsearchRepository<LipstickColorSearch, String> {
}

