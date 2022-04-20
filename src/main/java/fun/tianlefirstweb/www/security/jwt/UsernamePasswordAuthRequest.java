package fun.tianlefirstweb.www.security.jwt;

import lombok.Data;

@Data
public class UsernamePasswordAuthRequest {

    private String username;
    private String password;
}
