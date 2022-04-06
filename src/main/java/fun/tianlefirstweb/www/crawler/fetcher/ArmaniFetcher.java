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

@Component
public class ArmaniFetcher implements Fetcher {

    public Document getDocument() {
        WebClient webClient = new WebClient();
        webClient.getOptions().setJavaScriptEnabled(true);

        HtmlDivision div = null;
        try {
            HtmlPage page = webClient.getPage("https://www.giorgioarmanibeauty.cn/LIPS");
            webClient.waitForBackgroundJavaScript(5000);
            List<HtmlDivision> list = page.getByXPath("//div[contains(@class, 'product-main')]");
            div = list.get(0);
        } catch (IOException e) {
            //TODO: handle exception
        }
        return Jsoup.parse(div.asXml());
    }

    @Override
    public List<Lipstick> fetch() {
        List<Lipstick> lipsticks = new ArrayList<>();
        Document document = getDocument();
        Elements products = document.getElementsByClass("product-content");
        for (Element element : products) {
            Lipstick lipstick = getLipstickInfo(element);
            Elements colorElements = element.getElementsByTag("li");
            for (Element color : colorElements) {
                lipstick.addColor(getLipstickColorInfo(color));
            }
            lipsticks.add(lipstick);
        }
        return lipsticks;
    }

    @Override
    public String getBrandName() {
        return BrandInfo.ARMANI.name();
    }

    private Lipstick getLipstickInfo(Element lipstickDiv){
        String lipstickName = lipstickDiv.getElementsByTag("b").first().text();
        String lipstickUrl = lipstickDiv.getElementsByTag("img").first().attr("src");
        String lipstickPrice = lipstickDiv.getElementsByClass("price").text();
        return new Lipstick(lipstickName,lipstickPrice,lipstickUrl);
    }

    private LipstickColor getLipstickColorInfo(Element lipstickColorDiv){
        String colorName = lipstickColorDiv.attr("title");
        Elements backgroundColorElement = lipstickColorDiv.getElementsByTag("div");
        String backgroundColor = backgroundColorElement.first().attr("style");
        return new LipstickColor(colorName, backgroundColor);
    }
}
