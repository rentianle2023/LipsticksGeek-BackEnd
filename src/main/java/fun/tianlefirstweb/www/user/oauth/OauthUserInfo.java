package fun.tianlefirstweb.www.user.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OauthUserInfo {

    private String username;
    private String avatar;
    private String nickname;
    private String home;

    public OauthUserInfo(String jsonUserInfo, OauthUserInfo fields){
        JsonMapper mapper = new JsonMapper();
        try {
            JsonNode jsonNode = mapper.readTree(jsonUserInfo);
            this.avatar = jsonNode.get(fields.getAvatar()).asText();
            this.username = jsonNode.get(fields.getUsername()).asText();
            this.nickname = jsonNode.get(fields.getNickname()).asText();
            this.home = jsonNode.get(fields.getHome()).asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON parse error");
        }
    }
}
