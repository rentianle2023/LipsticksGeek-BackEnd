package fun.tianlefirstweb.www.user.oauth;

import org.springframework.data.repository.CrudRepository;

public interface OauthUserRepository extends CrudRepository<OauthUser,Integer> {

    boolean existsByUsernameAndProvider(String username, OauthProvider provider);

    OauthUser findByUsernameAndProvider(String username, OauthProvider provider);
}
