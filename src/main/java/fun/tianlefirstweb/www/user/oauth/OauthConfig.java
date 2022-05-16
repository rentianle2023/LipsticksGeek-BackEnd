package fun.tianlefirstweb.www.user.oauth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "application.oauth2.client")
@Data
public class OauthConfig {

    private Map<String, OauthRegistrationInfo> registrations = new HashMap<>();
    private Map<String, OauthUserInfo> jsonFields = new HashMap<>();
}
