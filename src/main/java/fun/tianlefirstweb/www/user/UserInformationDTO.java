package fun.tianlefirstweb.www.user;
import fun.tianlefirstweb.www.user.role.ApplicationRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInformationDTO {

    private Integer id;

    private String username;
    private String avatar;
    private List<ApplicationRole> roles;

    public UserInformationDTO(ApplicationUser user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.avatar = user.getAvatar();
        this.roles = user.getRoles();
    }
}
