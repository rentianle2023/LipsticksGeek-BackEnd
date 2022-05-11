package fun.tianlefirstweb.www.crawler;

public enum BrandInfo {

    MAC("https://www.maccosmetics.com.cn/products/13854","https://www.maccosmetics.com.cn"),
    YSL("https://www.yslbeautycn.com/makeup-lips-and-nails","https://www.yslbeautycn.com"),
    ARMANI("https://www.giorgioarmanibeauty.cn/LIPS","https://www.giorgioarmanibeauty.cn");

    private final String fetchUrl;
    private final String homeUrl;

    BrandInfo(String fetchUrl, String homeUrl) {
        this.fetchUrl = fetchUrl;
        this.homeUrl = homeUrl;
    }

    public String getFetchUrl() {
        return fetchUrl;
    }

    public String getHomeUrl() {
        return homeUrl;
    }
}
