package fun.tianlefirstweb.www.product.lipstickColor;

import com.fasterxml.jackson.annotation.JsonBackReference;
import fun.tianlefirstweb.www.product.lipstick.Lipstick;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @ManyToOne
    @JoinColumn(name = "lipstick_id")
    @JsonBackReference
    private Lipstick lipstick;

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
}
