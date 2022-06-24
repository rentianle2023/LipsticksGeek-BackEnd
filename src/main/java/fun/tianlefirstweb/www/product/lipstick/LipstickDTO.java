package fun.tianlefirstweb.www.product.lipstick;

import fun.tianlefirstweb.www.product.lipstickColor.LipstickColorDTO;
import lombok.Data;

@Data
public class LipstickDTO {

    private Integer id;
    private String name;
    private String price;
    private String imageUrl;
    private Boolean active;

    public LipstickDTO(Lipstick lipstick) {
        this.id = lipstick.getId();
        this.name = lipstick.getName();
        this.price = lipstick.getPrice();
        this.imageUrl = lipstick.getImageUrl();
        this.active = lipstick.getActive();
    }
}
