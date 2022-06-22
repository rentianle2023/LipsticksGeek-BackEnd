package fun.tianlefirstweb.www.user;

import fun.tianlefirstweb.www.user.role.ApplicationRole;
import fun.tianlefirstweb.www.user.role.RoleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static fun.tianlefirstweb.www.user.role.Role.USER;

@RestController
@RequestMapping("/users")
public class UserController {

    @Value("${application.user.default.avatar}")
    public String defaultAvatar;

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService,
                          RoleService roleService,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> userRegister(@Valid @RequestBody UserRegisterDTO userRegisterDTO){

        ApplicationRole userRole = roleService.findByRole(USER);

        ApplicationUser user = new ApplicationUser();
        user.setUsername(userRegisterDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setEmail(userRegisterDTO.getEmail());
        user.setAvatar(defaultAvatar);
        user.setRoles(List.of(userRole));
        userService.save(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<UserInformationDTO> getAuthenticatedUser(Authentication authentication){
        if(authentication == null || !authentication.isAuthenticated())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        ApplicationUser user = userService.findByUsername(authentication.getName());
        return ResponseEntity.ok(new UserInformationDTO(user));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserInformationDTO> getUser(@PathVariable String username){
        ApplicationUser user = userService.findByUsername(username);
        UserInformationDTO userInformation = new UserInformationDTO(user);
        return ResponseEntity.ok(userInformation);
    }
}
