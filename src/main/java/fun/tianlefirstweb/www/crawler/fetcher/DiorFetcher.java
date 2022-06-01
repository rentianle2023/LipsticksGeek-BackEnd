package fun.tianlefirstweb.www.crawler.fetcher;

import fun.tianlefirstweb.www.product.lipstick.Lipstick;
import fun.tianlefirstweb.www.product.lipstickColor.LipstickColor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static fun.tianlefirstweb.www.crawler.BrandInfo.DIOR;

@Component
@ConditionalOnProperty(prefix = "application.selenium",name = "enable",havingValue = "true")
public class DiorFetcher extends LipstickFetcher {

    private final ChromeDriver driver;

    public DiorFetcher(ChromeDriver driver) {
        this.driver = driver;
    }

    @Override
    protected List<Lipstick> fetchLipsticks() {
        try {
            driver.get(DIOR.getFetchUrl());
            ((JavascriptExecutor) driver)
                    .executeScript("window.scrollTo(0, document.body.scrollHeight / 5)");
            TimeUnit.SECONDS.sleep(3);
            ((JavascriptExecutor) driver)
                    .executeScript("window.scrollTo(0, document.body.scrollHeight * 2 / 5)");
            TimeUnit.SECONDS.sleep(3);
            ((JavascriptExecutor) driver)
                    .executeScript("window.scrollTo(0, document.body.scrollHeight * 3 / 5)");
            TimeUnit.SECONDS.sleep(3);
            ((JavascriptExecutor) driver)
                    .executeScript("window.scrollTo(0, document.body.scrollHeight * 4 / 5)");
            TimeUnit.SECONDS.sleep(3);
            ((JavascriptExecutor) driver)
                    .executeScript("window.scrollTo(0, document.body.scrollHeight)");
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<WebElement> elements = driver.findElements(new By.ByClassName("product-wrapper"));

        List<String> links = new ArrayList<>();
        elements.forEach(div -> {
            String lipstickLink = div.getAttribute("href");
            links.add(lipstickLink);
        });
        System.out.println(links.size());

        List<Lipstick> lipsticks = links.stream()
                .map(link -> getLipstick(driver, link).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        for (var lipstick : lipsticks) {
            System.out.println(lipstick.getName());
            System.out.println(lipstick.getColors().size());
        }

        return lipsticks;
    }

    private Optional<Lipstick> getLipstick(ChromeDriver driver, String url) {
        try {
            driver.get(url);
            AtomicReference<Integer> cnt = new AtomicReference<>(0);

            String lipstickImageUrl = driver.findElement(new By.ByClassName("product-media__image")).findElement(new By.ByTagName("img")).getAttribute("src");
            String lipstickName = driver.findElement(new By.ByClassName("product-titles")).findElement(new By.ByTagName("span")).getText();
            List<LipstickColor> colors = driver.findElement(new By.ByClassName("swatches-bloc")).findElements(new By.ByClassName("swatch"))
                    .stream().map(div -> {
                        String colorLink = div.findElement(new By.ByTagName("img")).getAttribute("src");
                        String color = getRGBfromImage(colorLink);
                        String name = div.findElement(By.tagName("label")).getText();
                        System.out.println(name + " " + color);
                        cnt.getAndSet(cnt.get() + 1);
                        if (cnt.get() == 10) {
                            ((JavascriptExecutor) driver)
                                    .executeScript("window.scrollTo(0, document.body.scrollHeight / 20)");
                        }
                        if (cnt.get() == 30) {
                            ((JavascriptExecutor) driver)
                                    .executeScript("window.scrollTo(0, document.body.scrollHeight / 15)");
                        }
                        return new LipstickColor(name, color);
                    }).collect(Collectors.toList());
            String lipstickPrice = driver.findElement(new By.ByClassName("variation-option-price")).getText();
            Lipstick lipstick = new Lipstick(lipstickName, lipstickPrice, lipstickImageUrl);
            lipstick.setColors(colors);
            System.out.println(cnt.get());
            System.out.println(lipstickName + ":" + lipstickPrice);
            System.out.println(lipstickImageUrl);
            return Optional.of(lipstick);
        } catch (Exception e) {
            System.out.println("跳过没有色号的套装");
            return Optional.empty();
        }
    }

    private String getRGBfromImage(String imageUrl) {
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
        return DIOR.name();
    }
}
