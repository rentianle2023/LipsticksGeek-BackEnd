package fun.tianlefirstweb.www.user.oauth;

import fun.tianlefirstweb.www.user.ApplicationUser;
import fun.tianlefirstweb.www.user.UserService;
import fun.tianlefirstweb.www.user.role.Role;
import fun.tianlefirstweb.www.user.role.ApplicationRole;
import fun.tianlefirstweb.www.user.role.RoleService;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class OauthService {

    private final RestTemplate restTemplate;
    private final OauthConfig oauthConfig;
    private final UserService userService;
    private final RoleService roleService;
    private final OauthUserRepository oauthUserRepository;

    public OauthService(RestTemplate restTemplate, OauthConfig oauthConfig, UserService userService, RoleService roleService, OauthUserRepository oauthUserRepository) {
        this.restTemplate = restTemplate;
        this.oauthConfig = oauthConfig;
        this.userService = userService;
        this.roleService = roleService;
        this.oauthUserRepository = oauthUserRepository;
    }


    public OauthAccessToken getToken(OauthProvider provider, String code){
        OauthRegistrationInfo registrationInfo = oauthConfig.getRegistrations().get(provider.getProvider());
        String tokenUri = provider.getTokenUri(
                registrationInfo.getClientId(),
                registrationInfo.getClientSecret(),
                code);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<?> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(
                tokenUri,
                HttpMethod.POST,
                entity,
                OauthAccessToken.class).getBody();
    }

    public OauthUserInfo getUserInfo(OauthProvider provider, String token){
        String userInfoUri = provider.getUserInfoUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> userInfo = restTemplate.exchange(userInfoUri,HttpMethod.GET,entity,String.class);
        OauthUserInfo fields = oauthConfig.getJsonFields().get(provider.getProvider());
        return new OauthUserInfo(userInfo.getBody(),fields);

    }

    public ApplicationUser registerOauthUser(String username, String avatar, String nickname, OauthProvider provider){
        ApplicationRole userRole = roleService.findByRole(Role.USER);
        ApplicationUser user = new ApplicationUser(
                getNonExistingUsername(username),
                UUID.randomUUID().toString(),
                avatar,
                nickname,
                List.of(userRole)
        );

        OauthUser oauthUser = new OauthUser(
                username,
                user,
                provider
        );
        user.setOAuthUsers(List.of(oauthUser));
        return userService.save(user);
    }

    public boolean existOauthUser(OauthUserInfo userInfo, OauthProvider provider) {
        return oauthUserRepository.existsByUsernameAndProvider(userInfo.getUsername(),provider);
    }

    public String getNonExistingUsername(String username){
        if(!userService.existsByUsername(username)) return username;

        RandomStringGenerator randomStringGenerator = new RandomStringGenerator.Builder()
                .withinRange('a','z').build();
        String originalUsername = username;

        do{
            username = originalUsername + randomStringGenerator.generate(5);
        }while(userService.existsByUsername(username));

        return username;
    }

    public ApplicationUser findByUsernameAndProvider(String username, OauthProvider provider){
        OauthUser oauthUser = oauthUserRepository.findByUsernameAndProvider(username, provider);
        return oauthUser.getUser();
    }
}
