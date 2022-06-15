package fun.tianlefirstweb.www.user.oauth;

import fun.tianlefirstweb.www.security.jwt.JwtConfig;
import fun.tianlefirstweb.www.user.ApplicationUser;
import io.jsonwebtoken.Jwts;
import org.springframework.http.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/oauth")
public class OauthController {

    private final OauthService oauthService;
    private final JwtConfig jwtConfig;

    public OauthController(OauthService oauthService, JwtConfig jwtConfig) {
        this.oauthService = oauthService;
        this.jwtConfig = jwtConfig;
    }

    @GetMapping("/{provider}/{code}")
    public ResponseEntity<?> githubLogin(@PathVariable("provider") OauthProvider provider,
                                         @PathVariable("code") String code,
                                         HttpServletResponse response){
        OauthAccessToken token = oauthService.getToken(provider, code);
        OauthUserInfo userInfo = oauthService.getUserInfo(provider, token.getAccessToken());
        ApplicationUser user;
        if(!oauthService.existOauthUser(userInfo,provider)){
            user = oauthService.registerOauthUser(userInfo.getUsername(), userInfo.getAvatar(), userInfo.getNickname(), OauthProvider.GITHUB);
        }else{
            user = oauthService.findByUsernameAndProvider(userInfo.getUsername(),provider);
        }
        List<SimpleGrantedAuthority> authorities = user.getRoles()
                .stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().name()))
                .collect(Collectors.toList());
        jwtConfig.signToken(user.getUsername(),authorities,response);
        return ResponseEntity.ok().build();
    }
}
