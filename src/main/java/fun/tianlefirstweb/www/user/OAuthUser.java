package fun.tianlefirstweb.www.user;

import fun.tianlefirstweb.www.user.enums.AuthenticationProvider;
import lombok.Data;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;

@Entity
@Data
public class OAuthUser {

    @Id
    private Integer id;

    @OneToOne
    @PrimaryKeyJoinColumn
    private ApplicationUser user;

    @Enumerated(STRING)
    private AuthenticationProvider authenticationProvider;

    private String oauthId;
}
