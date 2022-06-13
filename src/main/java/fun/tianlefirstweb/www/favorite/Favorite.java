package fun.tianlefirstweb.www.favorite;

import com.fasterxml.jackson.annotation.JsonBackReference;
import fun.tianlefirstweb.www.product.lipstickColor.LipstickColor;
import fun.tianlefirstweb.www.user.ApplicationUser;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference("favorite-users")
    private ApplicationUser user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "color_id")
    private LipstickColor color;

    private Timestamp createTime;

    public Favorite(ApplicationUser user, LipstickColor color, Timestamp createTime) {
        this.user = user;
        this.color = color;
        this.createTime = createTime;
    }
}
