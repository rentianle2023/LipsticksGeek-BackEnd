package fun.tianlefirstweb.www.search.lipstickColor;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;

import java.awt.*;

import static fun.tianlefirstweb.www.search.SearchIndices.LIPSTICK_COLOR;

@Document(indexName = LIPSTICK_COLOR)
@Data
public class LipstickColorSearch {

    @Id
    @Field(type = FieldType.Keyword)
    private String id;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String hexColor;

    @Field(type = FieldType.Float)
    private Float h;

    @Field(type = FieldType.Float)
    private Float s;

    @Field(type = FieldType.Float)
    private Float b;

    public LipstickColorSearch(String id, String name, String hexColor) {
        this.id = id;
        this.name = name;
        this.hexColor = hexColor;

        float[] hsb = ColorUtil.HEXtoHSB(hexColor);
        this.h = hsb[0];
        this.s = hsb[1];
        this.b = hsb[2];
    }


}
