package fun.tianlefirstweb.www.crawler.fetcher;

import fun.tianlefirstweb.www.product.lipstick.Lipstick;
import fun.tianlefirstweb.www.product.lipstickColor.LipstickColor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static fun.tianlefirstweb.www.crawler.BrandInfo.CHANEL;

@Component
public class ChanelFetcher extends LipstickFetcher{

    @Override
    protected List<Lipstick> fetchLipsticks() {
        Document document = null;
        try {
            document = Jsoup.connect(CHANEL.getFetchUrl()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document.getElementsByClass("fnb_inline")
                .stream().map(lipstickDiv -> {
                    String imageUrl = lipstickDiv.getElementsByClass("fnb_product-img").first().getElementsByTag("img").attr("src");
                    String name = lipstickDiv.getElementsByClass("fnb_product-title").first().text();
                    String description = lipstickDiv.getElementsByClass("fnb_product_grid_description").first().getElementsByTag("a").text();
                    String price = lipstickDiv.getElementsByClass("fnb_product-price").text();
                    Lipstick lipstick = new Lipstick(name + " " + description,price,CHANEL.getHomeUrl() + imageUrl);

                    List<LipstickColor> colors = lipstickDiv.getElementsByClass("fnb_shades-content")
                            .stream().map(colorDiv -> {
                                Element imgDiv = colorDiv.getElementsByTag("img").first();
                                String colorName = imgDiv.attr("alt");
                                String colorHex = getRGBfromImage(CHANEL.getHomeUrl() + imgDiv.attr("src"));
                                return new LipstickColor(colorName,colorHex);
                            }).collect(Collectors.toList());
                    lipstick.setColors(colors);
                    return lipstick;
                }).collect(Collectors.toList());
    }

    private static String getRGBfromImage(String imageUrl) {
        BufferedImage image = null;
        try {
            URL url = new URL(imageUrl);
            image = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int rgb = image.getRGB(0, 0);
        int red = (rgb & 0x00ff0000) >> 16;
        int green = (rgb & 0x0000ff00) >> 8;
        int blue = rgb & 0x000000ff;
        return String.format("#%02X%02X%02X", red, green, blue);
    }

    @Override
    protected String getBrandName() {
        return CHANEL.name();
    }
}
