package fun.tianlefirstweb.www.security;

import fun.tianlefirstweb.www.user.ApplicationUser;
import fun.tianlefirstweb.www.user.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser user;
        if(username.contains("@")){
            user = userService.findByEmail(username);
        }else user = userService.findByUsername(username);

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles()
                        .stream()
                        .map(role -> role.getRole().name())
                        .toArray(String[]::new))
                .build();
    }
}
