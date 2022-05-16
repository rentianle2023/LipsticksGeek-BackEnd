package fun.tianlefirstweb.www.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import fun.tianlefirstweb.www.favorite.Favorite;
import fun.tianlefirstweb.www.user.enums.Gender;
import fun.tianlefirstweb.www.user.oauth.OauthUser;
import fun.tianlefirstweb.www.user.role.ApplicationRole;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.sql.Date;
import java.util.List;

import static javax.persistence.EnumType.STRING;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user")
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String password;

    private String city;
    private String phoneNumber;
    private String email;
    private String avatar;
    private String nickname;

    private Date birthday;

    @Enumerated(STRING)
    private Gender gender;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<ApplicationRole> roles;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Favorite> favoriteColors;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<OauthUser> oAuthUsers;

    public ApplicationUser(String username, String password, String avatar, String nickname, List<ApplicationRole> roles) {
        this.username = username;
        this.password = password;
        this.avatar = avatar;
        this.nickname = nickname;
        this.roles = roles;
    }
}
