package fun.tianlefirstweb.www.search.lipstick;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface LipstickSearchRepository extends ElasticsearchRepository<LipstickSearch, String> {
}
