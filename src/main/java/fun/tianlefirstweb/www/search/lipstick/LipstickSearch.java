package fun.tianlefirstweb.www.search.lipstick;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;

import static fun.tianlefirstweb.www.search.SearchIndices.LIPSTICK;

@Document(indexName = LIPSTICK)
@Data
public class LipstickSearch {

    @Id
    @Field(type = FieldType.Keyword)
    private String id;

    @Field(type = FieldType.Text)
    private String name;

}
