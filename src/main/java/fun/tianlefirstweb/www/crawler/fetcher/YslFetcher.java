package fun.tianlefirstweb.www.crawler.fetcher;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
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
import java.util.Objects;

@Component
public class YslFetcher implements Fetcher {

    public Document getDocument() {
        try {
            return Jsoup.connect(BrandInfo.YSL.getFetchUrl()).get();
        } catch (IOException e) {
            throw new RuntimeException("获取网页失败");
        }
    }

    @Override
    public List<Lipstick> fetch() {
        List<Lipstick> lipsticks = new ArrayList<>();
        Document document = getDocument();
        Elements products = document.getElementsByClass("thumbnail");
        for (Element element : products) {
            Lipstick lipstick = getLipstickInfo(element);
            Elements colorElements = element.getElementsByTag("i");
            for (Element color : colorElements) {
                lipstick.addColor(getLipstickColorInfo(color));
            }
            lipsticks.add(lipstick);
        }
        return lipsticks;
    }

    @Override
    public String getBrandName() {
        return BrandInfo.YSL.name();
    }

    private Lipstick getLipstickInfo(Element lipstickDiv){
        String lipstickName = getSpecificElement(lipstickDiv,"goods-introudce","a").text();
        String lipstickUrl = getSpecificElement(lipstickDiv,"modUrl","img").attr("src");
        String lipstickPrice = getSpecificElement(lipstickDiv,"goods-price","span").text();
        return new Lipstick(lipstickName,lipstickPrice,lipstickUrl);
    }

    public Element getSpecificElement(Element lipstickDiv, String className, String tagName){
        return Objects.requireNonNull(lipstickDiv
                .getElementsByClass(className)
                .first())
                .getElementsByTag(tagName)
                .first();
    }

    private LipstickColor getLipstickColorInfo(Element lipstickColorDiv){
        String color = lipstickColorDiv.attr("style");
        String colorName = lipstickColorDiv.attr("title");
        return new LipstickColor(colorName, color);
    }
}
