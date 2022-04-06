package fun.tianlefirstweb.www.crawler.fetcher;

import fun.tianlefirstweb.www.crawler.BrandInfo;
import fun.tianlefirstweb.www.product.lipstick.Lipstick;
import fun.tianlefirstweb.www.product.lipstickColor.LipstickColor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MacFetcher implements Fetcher {

    public Document getDocument() {
        try {
            return Jsoup.connect(BrandInfo.MAC.getFetchUrl()).get();
        } catch (IOException e) {
            throw new RuntimeException("获取网页失败");
        }
    }

    @Override
    public List<Lipstick> fetch() {
        List<Lipstick> lipsticks = new ArrayList<>();
        Document document = getDocument();
        Elements products = document.getElementsByClass("product-brief");
        for (Element element : products) {
            Lipstick lipstick = getLipstickInfo(element);
            Elements colorElements = element.getElementsByAttributeValue("role", "gridcell");
            for (Element color : colorElements) {
                lipstick.addColor(getLipstickColorInfo(color));
            }
            lipsticks.add(lipstick);
        }
        return lipsticks;
    }

    @Override
    public String getBrandName() {
        return BrandInfo.MAC.name();
    }

    private Lipstick getLipstickInfo(Element lipstickDiv){
        String lipstickName = lipstickDiv.getElementsByTag("h3").first().text();
        String price = lipstickDiv.getElementsByClass("product-price").text();
        String imageUrl = BrandInfo.MAC.getHomeUrl() + lipstickDiv.getElementsByClass("product-brief__image-img").first().attr("data-src");
        return new Lipstick(lipstickName,price,imageUrl);
    }

    private LipstickColor getLipstickColorInfo(Element lipstickColorDiv){
        String backgroundColor = lipstickColorDiv.attr("style");
        String colorName = lipstickColorDiv.attr("title");
        return new LipstickColor(colorName, backgroundColor);
    }
}
