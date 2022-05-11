package fun.tianlefirstweb.www.product.lipstick;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import fun.tianlefirstweb.www.product.brand.Brand;
import fun.tianlefirstweb.www.product.lipstickColor.LipstickColor;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Entity
@Data
public class Lipstick {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(
            mappedBy = "lipstick",
            cascade = ALL,
            orphanRemoval = true,
            fetch = EAGER
    )
    @JsonManagedReference
    private List<LipstickColor> colors;

    private String name;
    private String price;
    private String imageUrl;

    @ManyToOne(fetch = LAZY)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lipstick lipstick = (Lipstick) o;
        return this.name.equals(lipstick.getName());
    }
}
