package fun.tianlefirstweb.www.search;

import fun.tianlefirstweb.www.search.lipstickColor.ColorUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class SearchUtil {

    public static SearchRequest buildSearchRequest(String indexName, SearchRequestDTO requestDTO){
        SearchRequest request = new SearchRequest(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().postFilter(getQueryBuilder(requestDTO));
        //searchSourceBuilder.size(100);
        return request.source(searchSourceBuilder);
    }

    public static SearchRequest buildSearchRequest(String indexName, String hexColor){
        SearchRequest request = new SearchRequest(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .postFilter(getQueryBuilder(hexColor));
        return request.source(searchSourceBuilder);
    }

    private static QueryBuilder getQueryBuilder(SearchRequestDTO requestDTO){
        //TODO: check valid request
        if(requestDTO.getFields().size() > 1){
            MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(requestDTO.getTerm())
                    .operator(Operator.AND)
                    .type(MultiMatchQueryBuilder.Type.CROSS_FIELDS);
            requestDTO.getFields()
                    .forEach(queryBuilder::field);
            return queryBuilder;
        }

        return QueryBuilders.matchBoolPrefixQuery(requestDTO.getFields().get(0),requestDTO.getTerm())
                .operator(Operator.AND);
    }

    private static QueryBuilder getQueryBuilder(String hexColor){
        //TODO: check valid request
        float[] hsb = ColorUtil.HEXtoHSB(hexColor);
        System.out.println(hsb[0] + " " + hsb[1] + " " + hsb[2]);
        return QueryBuilders.boolQuery()
                .must(QueryBuilders.rangeQuery("h").gte(hsb[0] - 0.05).lte(hsb[0] + 0.05))
                .must(QueryBuilders.rangeQuery("s").gte(hsb[1] - 0.05).lte(hsb[1] + 0.05))
                .must(QueryBuilders.rangeQuery("b").gte(hsb[2] - 0.05).lte(hsb[2] + 0.05));
    }
}
