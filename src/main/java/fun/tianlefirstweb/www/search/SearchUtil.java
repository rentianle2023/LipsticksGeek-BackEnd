package fun.tianlefirstweb.www.search;

import fun.tianlefirstweb.www.search.lipstickColor.ColorUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class SearchUtil {

    public static final double HUE_RANGE = 0.02;
    public static final double SATURATION_RANGE = 0.05;
    public static final double BRIGHTNESS_RANGE = 0.05;

    public static SearchRequest buildSearchRequest(String indexName, SearchRequestDTO requestDTO){
        SearchRequest request = new SearchRequest(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(getQueryBuilder(requestDTO));
        return request.source(searchSourceBuilder);
    }

    public static SearchRequest buildSearchRequest(String indexName, String hexColor){
        SearchRequest request = new SearchRequest(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .postFilter(getQueryBuilder(hexColor));
        searchSourceBuilder.size(100);
        return request.source(searchSourceBuilder);
    }

    private static QueryBuilder getQueryBuilder(SearchRequestDTO requestDTO){

        if(requestDTO.getFields().size() > 1){
            MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(requestDTO.getTerm())
                    .operator(Operator.AND)
                    .type(MultiMatchQueryBuilder.Type.CROSS_FIELDS);
            requestDTO.getFields()
                    .forEach(queryBuilder::field);
            return queryBuilder;
        }

        return QueryBuilders
                .matchBoolPrefixQuery(requestDTO.getFields().get(0),requestDTO.getTerm())
                .operator(Operator.AND);
    }

    private static QueryBuilder getQueryBuilder(String hexColor){

        float[] hsb = ColorUtil.HEXtoHSB(hexColor);
        float hue = hsb[0];

        BoolQueryBuilder builder = QueryBuilders.boolQuery()
                .must(QueryBuilders.rangeQuery("s").gte(hsb[1] - SATURATION_RANGE).lte(hsb[1] + SATURATION_RANGE))
                .must(QueryBuilders.rangeQuery("b").gte(hsb[2] - BRIGHTNESS_RANGE).lte(hsb[2] + BRIGHTNESS_RANGE));

        if(hue < HUE_RANGE){
            builder.filter(QueryBuilders.boolQuery()
                    .should(QueryBuilders.rangeQuery("h").gte((1 - HUE_RANGE) + hue))
                    .should(QueryBuilders.rangeQuery("h").lte(hue + HUE_RANGE)));
        }else if(hue > 1 - HUE_RANGE){
            builder.filter(QueryBuilders.boolQuery()
                    .should(QueryBuilders.rangeQuery("h").gte(hue - HUE_RANGE))
                    .should(QueryBuilders.rangeQuery("h").lte(hue - (1 - HUE_RANGE))));
        }else{
            builder.must(QueryBuilders.rangeQuery("h").gte(hsb[0] - HUE_RANGE).lte(hsb[0] + HUE_RANGE));
        }

        return builder;
    }
}
