package com.ccreanga.scrapper;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class SiteManager {

    public static Map<String,WebElement> getSuggestions(WebDriver driver, String user, String passwd) throws Exception {
        Map<String,WebElement> values = new HashMap<>();

        driver.get("https://www.linkedin.com");

        try {
            // todo - sometimes this page is the site landing, sometimes not
            WebElement signInButton = driver.findElement(By.className("nav__button-secondary"));
            signInButton.click();
            WebElement username = byId(driver,"username");
            WebElement password = byId(driver,"password");
            WebElement login = byCss(driver,".btn__primary--large.from__button--floating");
            System.out.println("cucu");

        }catch (NoSuchElementException e){
            WebElement username = byId(driver,"login-email");
            WebElement password = byId(driver,"login-password");
            WebElement login = byId(driver,"login-submit");
            username.sendKeys(user);
            password.sendKeys(passwd);
            login.click();
        }


        WebElement network = byId(driver,"mynetwork-tab-icon");
        network.click();

        List<WebElement> elements = listByCss(driver,".discover-person-card.artdeco-card.ember-view","can't find connect panel");
        for (WebElement next : elements) {
            //profile url
            String profileLink = byCss(next,".discover-person-card__link.ember-view","can't find profile link").getAttribute("href");
            WebElement connectButton = byCss(
                    next,".js-discover-person-card__action-btn.full-width.artdeco-button.artdeco-button--2.artdeco-button--full.artdeco-button--secondary.ember-view","can't find connect button");
            Thread.sleep(2000+(int)(Math.random()*4000));
            values.put(profileLink,connectButton);
        }
        return values;
    }

    public static WebElement byId(SearchContext searchContext, String id){
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
