package fun.tianlefirstweb.www.user;

import fun.tianlefirstweb.www.exception.UserAlreadyExistException;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final ApplicationUserRepository userRepository;

    public UserService(ApplicationUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ApplicationUser save(ApplicationUser user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistException("注册用户名已存在");
        } else if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistException("注册邮箱已存在");
        }
        return userRepository.save(user);
    }

    public ApplicationUser findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AuthenticationServiceException("username doesn't exist"));
    }

    public ApplicationUser findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationServiceException("username doesn't exist"));
    }

    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }
}
