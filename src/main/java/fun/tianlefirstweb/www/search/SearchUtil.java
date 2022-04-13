package fun.tianlefirstweb.www.search;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

public class SearchUtil {

    public static SearchRequest buildSearchRequest(String indexName, SearchRequestDTO requestDTO){
        SearchRequest request = new SearchRequest(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().postFilter(getQueryBuilder(requestDTO));
//        searchSourceBuilder.size(100);
        return request.source(searchSourceBuilder);
    }

    public static QueryBuilder getQueryBuilder(SearchRequestDTO requestDTO){
        //TODO: check valid request
        if(requestDTO.getFields().size() > 1){
            MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(requestDTO.getTerm())
                    .operator(Operator.AND)
                    .type(MultiMatchQueryBuilder.Type.CROSS_FIELDS);
            requestDTO.getFields()
                    .forEach(queryBuilder::field);
            return queryBuilder;
        }

        return QueryBuilders.matchQuery(requestDTO.getFields().get(0),requestDTO.getTerm())
                .operator(Operator.AND);
    }
}
