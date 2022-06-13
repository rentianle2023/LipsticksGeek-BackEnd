package fun.tianlefirstweb.www;

import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;
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

@SpringBootApplication
@RestController
@EncryptablePropertySource(name = "EncryptedProperties", value = {"classpath:application-dev.yaml","classpath:application-prod.yaml"})
public class LipstickApplication {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder)
    {
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(500))
                .setReadTimeout(Duration.ofSeconds(500))
                .build();
    }
    public static void main(String[] args) {
        SpringApplication.run(LipstickApplication.class, args);
    }
}
