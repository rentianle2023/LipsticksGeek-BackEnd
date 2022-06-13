package fun.tianlefirstweb.www.product.lipstickColor;

import com.fasterxml.jackson.annotation.JsonBackReference;
import fun.tianlefirstweb.www.favorite.Favorite;
import fun.tianlefirstweb.www.product.lipstick.Lipstick;
import fun.tianlefirstweb.www.user.ApplicationUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LipstickColor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String hexColor;

    @JsonBackReference("lipstick-colors")
    @ManyToOne
    @JoinColumn(name = "lipstick_id")
    private Lipstick lipstick;

//    private Integer hue;
//    private Integer saturation;
//    private Integer brightness;

    @JsonBackReference("favorite-users")
    @OneToMany(mappedBy = "color")
    private List<Favorite> favoriteUsers;

    public LipstickColor(String name, String hexColor) {
        this.name = name;
        this.hexColor = hexColor;
        trimHexColor();
    }

    public void trimHexColor(){
        int beginIndex = hexColor.indexOf("#");
        if(beginIndex == -1 || beginIndex + 7 > hexColor.length()) return;
        this.hexColor = hexColor.substring(beginIndex,beginIndex + 7);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LipstickColor color = (LipstickColor) o;
        return this.name.equals(color.getName()) || this.hexColor.equals(color.getHexColor());
    }
}
