package fun.tianlefirstweb.www.user.oauth;

import com.fasterxml.jackson.annotation.JsonBackReference;
import fun.tianlefirstweb.www.user.ApplicationUser;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class OauthUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    @ManyToOne
    @JsonBackReference
    private ApplicationUser user;

    @Enumerated(EnumType.STRING)
    private OauthProvider provider;

    public OauthUser(String username, ApplicationUser user, OauthProvider provider) {
        this.username = username;
        this.user = user;
        this.provider = provider;
    }
}
