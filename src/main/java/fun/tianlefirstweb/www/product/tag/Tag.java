package fun.tianlefirstweb.www.product.tag;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fun.tianlefirstweb.www.product.lipstickColor.LipstickColor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(value = EnumType.STRING)
    private TagTitle title;

    @ManyToMany
    @JoinTable(
            name = "color_tag",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "color_id"))
    @JsonIgnore
    private List<LipstickColor> colors;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id) && title == tag.title;
    }
}
