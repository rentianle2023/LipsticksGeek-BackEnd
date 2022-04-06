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
    private String backgroundColor;

    @ManyToOne
    @JoinColumn(name = "lipstick_id")
    @JsonBackReference
    private Lipstick lipstick;

    public LipstickColor(String name, String backgroundColor) {
        this.name = name;
        this.backgroundColor = backgroundColor;
        trimBackgroundColor();
    }

    public void trimBackgroundColor(){
        int beginIndex = backgroundColor.indexOf("#");
        if(beginIndex == -1 || beginIndex + 7 > backgroundColor.length()) return;
        this.backgroundColor = backgroundColor.substring(beginIndex,beginIndex + 7);
    }
}
