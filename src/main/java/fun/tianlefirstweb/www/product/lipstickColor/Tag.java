package fun.tianlefirstweb.www.product.lipstickColor;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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
}
