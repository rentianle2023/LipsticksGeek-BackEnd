package fun.tianlefirstweb.www.product.lipstickColor;

import fun.tianlefirstweb.www.product.brand.Brand;
import fun.tianlefirstweb.www.product.lipstick.Lipstick;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LipstickColorDetailsDTO {

    private Integer id;
    private String hexColor;
    private String name;

    private Integer lipstickId;
    private String lipstickName;
    private String lipstickPrice;

    private Integer brandId;
    private String brandName;

    public LipstickColorDetailsDTO(LipstickColor color) {
        setColor(color);
        setBrand(color.getLipstick().getBrand());
        setLipstick(color.getLipstick());
    }

    public void setColor(LipstickColor color) {
        this.id = color.getId();
        this.hexColor = color.getHexColor();
        this.name = color.getName();
    }

    public void setLipstick(Lipstick lipstick) {
        this.lipstickId = lipstick.getId();
        this.lipstickName = lipstick.getName();
        this.lipstickPrice = lipstick.getPrice();
    }

    public void setBrand(Brand brand) {
        this.brandId = brand.getId();
        this.brandName = brand.getName();
    }
}
