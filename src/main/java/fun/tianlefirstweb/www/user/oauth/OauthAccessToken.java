package fun.tianlefirstweb.www.user.oauth;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OauthAccessToken {

    @JsonAlias("access_token")
    private String accessToken;
    private String scope;
    @JsonAlias("token_type")
    private String tokenType;
}
