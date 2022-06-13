package fun.tianlefirstweb.www.comment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import fun.tianlefirstweb.www.user.ApplicationUser;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Timestamp createTime;
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser user;

    @OneToMany(
            mappedBy = "comment",
            fetch = FetchType.LAZY)
    private List<Reply> replies;
}
