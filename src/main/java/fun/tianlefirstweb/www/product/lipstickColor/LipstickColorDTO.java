package fun.tianlefirstweb.www.product.lipstickColor;

import lombok.Data;

@Data
public class LipstickColorDTO {

    private Integer id;
    private String name;
    private String hexColor;

    public LipstickColorDTO(LipstickColor lipstickColor) {
        this.id = lipstickColor.getId();
        this.name = lipstickColor.getName();
        this.hexColor = lipstickColor.getHexColor();
    }
}
