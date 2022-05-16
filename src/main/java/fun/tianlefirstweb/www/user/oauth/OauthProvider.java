package fun.tianlefirstweb.www.user.oauth;

public enum OauthProvider {

    GITHUB("github",
            "https://github.com/login/oauth/access_token",
            "https://api.github.com/user"),

    GOOGLE("google",
                   "",
                   "");

    private final String provider;
    private final String tokenUri;
    private final String userInfoUri;

    OauthProvider(String provider, String tokenUri, String userInfoUri) {
        this.provider = provider;
        this.tokenUri = tokenUri;
        this.userInfoUri = userInfoUri;
    }

    public String getProvider() {
        return provider;
    }

    public String getTokenUri(String clientId, String clientSecret, String code){
        return String.format("%s?client_id=%s&client_secret=%s&code=%s",
                tokenUri,
                clientId,
                clientSecret,
                code);
    }

    public String getUserInfoUri() {
        return userInfoUri;
    }
}
