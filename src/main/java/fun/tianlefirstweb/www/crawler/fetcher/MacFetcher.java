package fun.tianlefirstweb.www.crawler.fetcher;

import fun.tianlefirstweb.www.crawler.BrandInfo;
import fun.tianlefirstweb.www.exception.UnableToConnectException;
import fun.tianlefirstweb.www.product.brand.BrandRepository;
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

import static fun.tianlefirstweb.www.crawler.BrandInfo.MAC;

@Component
public class MacFetcher extends LipstickFetcher {

    public Document getDocument() {
        try {
            return Jsoup.connect(MAC.getFetchUrl()).get();
        } catch (IOException e) {
            throw new UnableToConnectException(String.format("Failed to connect %s's website",getBrandName()));
        }
    }

    @Override
    public List<Lipstick> fetchLipsticks() {
        List<Lipstick> lipsticks = new ArrayList<>();
        Document document = getDocument();
        Elements products = document.getElementsByClass("product-brief");
        for (Element element : products) {
            Lipstick lipstick = getLipstickInfo(element);
            lipstick.setColors(new ArrayList<>());
            Elements colorElements = element.getElementsByAttributeValue("role", "gridcell");
            for (Element color : colorElements) {
                lipstick.getColors().add(getLipstickColorInfo(color));
            }
            lipsticks.add(lipstick);
        }
        return lipsticks;
    }

    @Override
    public String getBrandName() {
        return MAC.name();
    }

    private Lipstick getLipstickInfo(Element lipstickDiv){
        String lipstickName = lipstickDiv.getElementsByTag("h3").first().text();
        String price = lipstickDiv.getElementsByClass("product-price").text();
        String imageUrl = MAC.getHomeUrl() + lipstickDiv.getElementsByClass("product-brief__image-img").first().attr("data-src");
        return new Lipstick(lipstickName,price,imageUrl);
    }

    private LipstickColor getLipstickColorInfo(Element lipstickColorDiv){
        String color = lipstickColorDiv.attr("style");
        String colorName = lipstickColorDiv.attr("title");
        return new LipstickColor(colorName, color);
    }
}
