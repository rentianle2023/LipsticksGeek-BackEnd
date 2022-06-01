package fun.tianlefirstweb.www.crawler;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@ConditionalOnProperty(prefix = "application.selenium",name = "enable",havingValue = "true")
public class SeleniumConfig {

    @PostConstruct
    private void postConstruct(){
        System.setProperty("webdriver.chrome.driver","D:\\ChromeDriver\\chromedriver.exe");
    }

    @Bean
    public ChromeDriver chromeDriver(){
        return new ChromeDriver();
    }
}
