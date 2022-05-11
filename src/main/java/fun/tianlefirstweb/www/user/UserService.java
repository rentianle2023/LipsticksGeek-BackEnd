package fun.tianlefirstweb.www.user;

import fun.tianlefirstweb.www.exception.EntityAlreadyExistException;
import fun.tianlefirstweb.www.exception.EntityNotExistException;
import fun.tianlefirstweb.www.exception.UserAlreadyExistException;
import fun.tianlefirstweb.www.product.lipstickColor.LipstickColor;
import fun.tianlefirstweb.www.user.enums.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final ApplicationUserRepository userRepository;
    private final OAuthUserRepository oauthUserRepository;

    public UserService(ApplicationUserRepository userRepository, OAuthUserRepository oauthUserRepository) {
        this.userRepository = userRepository;
        this.oauthUserRepository = oauthUserRepository;
    }

    public void save(ApplicationUser user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistException("注册用户名已存在");
        } else if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistException("注册邮箱已存在");
        }
        userRepository.save(user);
    }

    public ApplicationUser findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AuthenticationServiceException("username doesn't exist"));
    }

    public ApplicationUser findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationServiceException("username doesn't exist"));
    }

    public ApplicationUser getOauthUser(String id, AuthenticationProvider provider) {
        OAuthUser oAuthUser = oauthUserRepository.getByOauthIdAndAuthenticationProvider(id, provider)
                .orElseThrow(() -> new AuthenticationServiceException(""));

        return oAuthUser.getUser();
    }
}
