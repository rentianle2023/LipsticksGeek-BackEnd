package fun.tianlefirstweb.www;

import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;
import fun.tianlefirstweb.www.crawler.fetcher.DiorFetcher;
import fun.tianlefirstweb.www.product.lipstick.Lipstick;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
@EncryptablePropertySource(name = "EncryptedProperties", value = {"classpath:application-dev.yaml","classpath:application-prod.yaml"})
public class LipstickApplication implements ApplicationRunner {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder)
    {
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(3))
                .setReadTimeout(Duration.ofSeconds(6))
                .build();
    }
    public static void main(String[] args) {
        SpringApplication.run(LipstickApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Map<Integer, Integer> map = Map.of(1,2);
    }
}
