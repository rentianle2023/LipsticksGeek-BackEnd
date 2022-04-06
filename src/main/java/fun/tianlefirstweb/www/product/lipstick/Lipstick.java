package fun.tianlefirstweb.www.product.lipstick;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import fun.tianlefirstweb.www.product.brand.Brand;
import fun.tianlefirstweb.www.product.lipstickColor.LipstickColor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Lipstick {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(
            mappedBy = "lipstick",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<LipstickColor> colors;

    private String name;
    private String price;
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    @JsonBackReference
    private Brand brand;

    public Lipstick() {
    }

    public Lipstick(String name, String price, String imageUrl) {
        this.colors = new ArrayList<>();
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public void addColor(LipstickColor color){
        colors.add(color);
        color.setLipstick(this);
    }
}
