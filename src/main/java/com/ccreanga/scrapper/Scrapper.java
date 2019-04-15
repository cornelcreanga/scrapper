package com.ccreanga.scrapper;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Scrapper implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Scrapper.class, args);
    }

    @Override
    public void run(String... args) throws Exception{
        SeleniumUtils.locateChromeDriver(null);
        String user = System.getenv("LINKEDIN_USER");
        String passwd = System.getenv("LINKEDIN_PASSWD");
        if (((user==null) || (passwd==null)) && (args.length==2)){
            user = args[0];
            passwd = args[1];
        }
        if ((user==null) || (passwd==null)){
            log.info("user/passwd should be configured as env variables (LINKEDIN_USER/LINKEDIN_PASSWD) or passed as arguments");
            System.exit(1);
        }
        WebDriver driver = SeleniumUtils.createWebDriver();
        driver.get("https://www.linkedin.com");
        WebElement username = byId(driver,"login-email");
        WebElement password = byId(driver,"login-password");
        WebElement login = byId(driver,"login-submit");
        username.sendKeys(user);
        password.sendKeys(passwd);
        login.click();
        WebElement network = byId(driver,"mynetwork-tab-icon");
        network.click();

        List<WebElement> elements = listByCss(driver,".discover-person-card.artdeco-card.ember-view","can't find connect panel");
        for (WebElement next : elements) {
            //profile url
            String profileLink = byCss(next,".discover-person-card__link.ember-view","can't find profile link").getAttribute("href");
            WebElement connectButton = byCss(
                next,".js-discover-person-card__action-btn.full-width.artdeco-button.artdeco-button--2.artdeco-button--full.artdeco-button--secondary.ember-view","can't find connect button");
            Thread.sleep(2000+(int)(Math.random()*4000));
            log.info("Inviting {}",profileLink);
            connectButton.click();

        }
        System.out.println(elements.size());

    }

    public static WebElement byId(SearchContext searchContext,String id){
        return byId(searchContext,id,"can't find the element identified by ");
    }

    public static WebElement byId(SearchContext searchContext,String id,String message){
        try {
            return searchContext.findElement(By.id(id));
        }catch (NoSuchElementException e){
            log.error("{} id {}",message,id);
            System.exit(1);
        }
        return null;//never reached
    }

    public static WebElement byCss(SearchContext searchContext,String id){
        return byCss(searchContext,id,"can't find the element identified by ");
    }

    public static WebElement byCss(SearchContext searchContext,String id,String message){
        try {
            return searchContext.findElement(By.cssSelector(id));
        }catch (NoSuchElementException e){
            log.error("{} cssSelector {}",message,id);
            System.exit(1);
        }
        return null;//never reached
    }

    public static List<WebElement> listByCss(SearchContext searchContext,String id){
        return listByCss(searchContext,id,"can't find the element identified by ");
    }

    public static List<WebElement> listByCss(SearchContext searchContext,String id,String message){
        try {
            return searchContext.findElements(By.cssSelector(id));
        }catch (NoSuchElementException e){
            log.error("{} cssSelector {}",message,id);
            System.exit(1);
        }
        return null;//never reached
    }

}
