package fun.tianlefirstweb.www.crawler.fetcher;

import fun.tianlefirstweb.www.product.lipstick.Lipstick;
import fun.tianlefirstweb.www.product.lipstickColor.LipstickColor;
import fun.tianlefirstweb.www.search.lipstickColor.LipstickColorSearch;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static fun.tianlefirstweb.www.crawler.BrandInfo.GIVENCHY;

@Component
public class GivenchyFetcher extends LipstickFetcher {

    @Override
    protected List<Lipstick> fetchLipsticks() {
        Document document = null;
        try {
            document = Jsoup.connect(GIVENCHY.getFetchUrl()).get();
        } catch (IOException e) {
            throw new RuntimeException("抓取官网页面失败");
        }
        List<Lipstick> lipsticks = new ArrayList<>();
        document.getElementsByClass("giv-ProductTile-name")
                .forEach(div -> {
                    String lipstickLink = "https://www.givenchybeauty.cn" +
                            div.getElementsByTag("a").attr("href");

                    fetchLipstick(lipstickLink).ifPresent(lipsticks::add);
                });
        return lipsticks;
    }

    protected Optional<Lipstick> fetchLipstick(String url) {
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            return Optional.empty();
        }
        String lipstickName = document.getElementsByClass("product-name").text();
        Element productContent = document.getElementById("product-content");
        if (lipstickName.endsWith("壳") || productContent == null) return Optional.empty();

        List<LipstickColor> colors = productContent
                .getElementsByTag("aside").first()
                .getElementsByClass("selectable")
                .stream()
                .map(div -> {
                    Elements spans = div.getElementsByTag("span");
                    String hexColor = spans.get(0).getElementsByTag("span").attr("style").split(":")[1];

                    if (hexColor.startsWith("r")) {
                        hexColor = RGBtoHEX(hexColor);
                    }

                    String colorName = div.getElementsByTag("div").first().text();
                    String[] splits = colorName.split("N°");
                    return new LipstickColor("N°" + splits[splits.length - 1], hexColor);
                }).collect(Collectors.toList());

        Lipstick lipstick = new Lipstick();
        lipstick.setName(lipstickName);
        lipstick.setColors(colors);

        return Optional.of(lipstick);
    }

    private String RGBtoHEX(String color) {
        String colors = color.substring(4, color.length() - 1);
        String[] rgb = colors.split(",");
        return String.format("#%X%X%X",
                Integer.valueOf(rgb[0].trim()),
                Integer.valueOf(rgb[1].trim()),
                Integer.valueOf(rgb[2].trim()));
    }

    @Override
    protected String getBrandName() {
        return GIVENCHY.name();
    }
}
