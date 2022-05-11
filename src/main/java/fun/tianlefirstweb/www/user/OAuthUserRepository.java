package fun.tianlefirstweb.www.user;

import fun.tianlefirstweb.www.user.enums.AuthenticationProvider;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OAuthUserRepository extends CrudRepository<OAuthUser,Integer> {


    Optional<OAuthUser> getByOauthIdAndAuthenticationProvider(String oauthId, AuthenticationProvider provider);
}
