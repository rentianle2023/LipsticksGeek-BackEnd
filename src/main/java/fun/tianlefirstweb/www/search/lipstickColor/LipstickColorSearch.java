package fun.tianlefirstweb.www.search.lipstickColor;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;

import static fun.tianlefirstweb.www.search.SearchIndices.LIPSTICK_COLOR;

@Document(indexName = LIPSTICK_COLOR)
@Data
public class LipstickColorSearch {

    @Id
    @Field(type = FieldType.Keyword)
    private final String id;

    @Field(type = FieldType.Text)
    private final String brandName;

    @Field(type = FieldType.Text)
    private final String name;

    @Field(type = FieldType.Text)
    private final String hexColor;

    @Field(type = FieldType.Float)
    private final Float h;

    @Field(type = FieldType.Float)
    private final Float s;

    @Field(type = FieldType.Float)
    private final Float b;

    public LipstickColorSearch(String id, String brandName, String name, String hexColor) {
        this.id = id;
        this.brandName = brandName;
        this.name = name;
        this.hexColor = hexColor;

        float[] hsb = ColorUtil.HEXtoHSB(hexColor);
        this.h = hsb[0];
        this.s = hsb[1];
        this.b = hsb[2];
    }


}
