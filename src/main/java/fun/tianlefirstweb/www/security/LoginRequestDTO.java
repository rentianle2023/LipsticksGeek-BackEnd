package fun.tianlefirstweb.www.security;

import lombok.Data;

@Data
public class LoginRequestDTO {

    private String username;
    private String password;
}
