package com.ccreanga.scrapper;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Scrapper implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Scrapper.class, args);
    }

    @Override
    public void run(String... args) {
        SeleniumUtils.locateChromeDriver(null);
        String user = System.getenv("LINKEDIN_USER");
        String passwd = System.getenv("LINKEDIN_PASSWD");
        WebDriver driver = SeleniumUtils.createWebDriver();
        driver.get("https://www.linkedin.com");
        WebElement username = driver.findElement(By.id("login-email"));
        WebElement password = driver.findElement(By.id("login-password"));
        WebElement login = driver.findElement(By.id("login-submit"));
        username.sendKeys(user);
        password.sendKeys(passwd);
        login.click();
        WebElement network = driver.findElement(By.id("mynetwork-tab-icon"));
        network.click();

        List<WebElement> elements = driver.findElements(By.cssSelector(".discover-person-card.artdeco-card.ember-view"));
        for (WebElement next : elements) {
            //profile url
            System.out.println(next.findElement(By.cssSelector(".discover-person-card__link.ember-view")).getAttribute("href"));
        }
        System.out.println(elements.size());


    }

}
