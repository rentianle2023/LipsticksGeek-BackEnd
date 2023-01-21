package fun.tianlefirstweb.www.product.lipstickColor;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import fun.tianlefirstweb.www.favorite.Favorite;
import fun.tianlefirstweb.www.product.lipstick.Lipstick;
import fun.tianlefirstweb.www.product.tag.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

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
    private ZonedDateTime createDateTime;

    @JsonBackReference("lipstick-colors")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lipstick_id",updatable = false)
    private Lipstick lipstick;

    @JsonBackReference("favorite-users")
    @OneToMany(mappedBy = "color")
    private List<Favorite> favoriteUsers;

    @ManyToMany
    @JoinTable(
            name = "color_tag",
            joinColumns = @JoinColumn(name = "color_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Tag> tags;

    public LipstickColor(String name, String hexColor) {
        this.name = name;
        this.hexColor = hexColor;
        this.createDateTime = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"));
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
