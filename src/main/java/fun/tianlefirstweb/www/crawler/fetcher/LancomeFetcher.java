package fun.tianlefirstweb.www.crawler.fetcher;

import fun.tianlefirstweb.www.product.lipstick.Lipstick;
import fun.tianlefirstweb.www.product.lipstickColor.LipstickColor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static fun.tianlefirstweb.www.crawler.BrandInfo.LANCOME;

@Component
public class LancomeFetcher extends LipstickFetcher {


    @Override
    protected List<Lipstick> fetchLipsticks() {
        Document document = null;
        try {
            document = Jsoup.connect(LANCOME.getFetchUrl()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return document.getElementsByClass("thumbnail")
                .stream().map(div -> {

                    String price = div.getElementsByClass("goods-price").first().text();
                    Element imageElement = div.getElementsByClass("modUrl").first().getElementsByTag("img").first();

                    String name = imageElement.attr("alt");
                    String imageUrl = imageElement.attr("src");

                    Lipstick lipstick = new Lipstick(name, price, imageUrl);

                    lipstick.setColors(div.getElementsByClass("swiper-slide")
                            .stream()
                            .map(color -> {
                                Element colorSpan = color.getElementsByTag("span").first();
                                String hexColor = colorSpan.attr("style").split(" ")[1].substring(0, 7);
                                String colorName = colorSpan.attr("title");
                                return new LipstickColor(colorName, hexColor);
                            }).collect(Collectors.toList()));
                    return lipstick;
                }).collect(Collectors.toList());
    }

    @Override
    protected String getBrandName() {
        return LANCOME.name();
    }
}
