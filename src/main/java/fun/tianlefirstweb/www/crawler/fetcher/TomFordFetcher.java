package fun.tianlefirstweb.www.crawler.fetcher;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import fun.tianlefirstweb.www.product.lipstick.Lipstick;
import fun.tianlefirstweb.www.product.lipstickColor.LipstickColor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static fun.tianlefirstweb.www.crawler.BrandInfo.TOMFORD;

@Component
public class TomFordFetcher extends LipstickFetcher{

    private Document getDocument(String url){
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    private Document getDocument(){
        WebClient webClient = new WebClient();
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        try {
            HtmlPage page = webClient.getPage(TOMFORD.getFetchUrl());
            webClient.waitForBackgroundJavaScript(5000);
            return Jsoup.parse(page.asXml());
        } catch (IOException e) {
            throw new RuntimeException("无法获取网页");
        }
    }

    @Override
    protected List<Lipstick> fetchLipsticks() {
        Document document = getDocument();

        return document.getElementsByClass("product-tile")
                .stream()
                .parallel()
                .map(div -> {

                    Element linkElement = div.getElementsByClass("name-link").first();
                    String link = linkElement.attr("href");
                    Lipstick lipstick = getLipstick(link);
                    String name = linkElement.text();
                    String price = div.getElementsByClass("product-sales-price").first().text();

                    lipstick.setPrice(price);
                    lipstick.setName(name);
                    return lipstick;
        }).collect(Collectors.toList());
    }

    private Lipstick getLipstick(String url) {
        Document document = getDocument(url);
        Lipstick lipstick = new Lipstick();

        //imageUrl
        String imageUrl = document.getElementsByClass("primary-image")
                .attr("src");
        lipstick.setImageUrl(imageUrl);

        List<LipstickColor> colors = new ArrayList<>();
        document.getElementsByClass("swatches")
                .first()
                .getElementsByTag("li")
                .forEach(li -> {
                    Elements aTag = li.getElementsByTag("a");
                    String colorLink = aTag.attr("style").split(" ")[1];
                    colorLink = colorLink.substring(4,colorLink.length() - 1);
                    String hexColor = getRGBfromImage(colorLink);
                    String colorName = aTag.text();

                    colors.add(new LipstickColor(colorName,hexColor));
                });
        lipstick.setColors(colors);

        return lipstick;
    }

    private String getRGBfromImage(String imageUrl){
        BufferedImage image = null;
        try {
            URL url = new URL("https:" + imageUrl);
            image = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int rgb = image.getRGB(0, 0);
        int red =   (rgb & 0x00ff0000) >> 16;
        int green = (rgb & 0x0000ff00) >> 8;
        int blue =   rgb & 0x000000ff;
        return String.format("#%02X%02X%02X",red,green,blue);
    }

    @Override
    protected String getBrandName() {
        return TOMFORD.name();
    }
}
