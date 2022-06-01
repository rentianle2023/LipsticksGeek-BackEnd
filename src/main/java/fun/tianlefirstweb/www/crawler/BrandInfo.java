package fun.tianlefirstweb.www.crawler;

public enum BrandInfo {

    MAC("https://www.maccosmetics.com.cn/products/13854","https://www.maccosmetics.com.cn"),
    YSL("https://www.yslbeautycn.com/makeup-lips-and-nails","https://www.yslbeautycn.com"),
    ARMANI("https://www.giorgioarmanibeauty.cn/LIPS","https://www.giorgioarmanibeauty.cn"),
    TOMFORD("https://www.tomford.com/beauty/lips/","https://www.tomford.com"),
    LANCOME("https://www.lancome.com.cn/l2_makeup_lips/","https://www.lancome.com.cn"),
    GIVENCHY("https://www.givenchybeauty.cn/%E5%BD%A9%E5%A6%86/%E5%94%87%E9%83%A8/","https://www.givenchybeauty.cn"),
    DIOR("https://www.dior.cn/zh_cn/beauty/makeup/lips/all-products","https://www.dior.cn/zh_cn/beauty"),
    CHANEL("https://www.chanel.cn/zh_CN/fragrance-beauty/makeup/p/lips.html","https://www.chanel.cn");

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
