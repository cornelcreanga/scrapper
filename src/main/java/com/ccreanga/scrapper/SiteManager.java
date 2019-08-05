package com.ccreanga.scrapper;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ccreanga.scrapper.Util.sleep;

@Slf4j
public class SiteManager {

    public static Map<String,WebElement> getSuggestions(WebDriver driver, String user, String passwd) throws Exception {
        Map<String,WebElement> values = new HashMap<>();

        driver.get("https://www.linkedin.com");
        Capabilities capabilities = ((ChromeDriver)driver).getCapabilities();
        //https://github.com/bonigarcia/webdrivermanager
        //browserVersion -> 76.0.3809.87
        //browserName -> chrome - map
        //"chromedriverVersion" -> "76.0.3809.68 (420c9498db8ce8fcd190a954d51297672c1515d5-refs/branch-heads/3809@{#864})"
        //"userDataDir" -> "/var/folders/k2/bfwstqws2xd38c7620bc9jxw0000gn/T/.com.google.Chrome.Knb97T"
        try {
            // todo - sometimes this page is the site landing, sometimes not
            WebElement signInButton = driver.findElement(By.className("nav__button-secondary"));
            signInButton.click();
            WebElement username = byId(driver,"username");
            WebElement password = byId(driver,"password");
            WebElement login = byCss(driver,".btn__primary--large.from__button--floating");
            username.sendKeys(user);
            password.sendKeys(passwd);
            login.click();

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
        sleep(10+(int)(Math.random()*4));
        List<WebElement> elements = listByCss(driver,".discover-person-card.artdeco-card.ember-view","can't find connect panel");
        for (WebElement next : elements) {
            //profile url
            String profileLink = byCss(next,".discover-person-card__link.ember-view","can't find profile link").getAttribute("href");
            WebElement connectButton = byCss(
                    next,".js-discover-person-card__action-btn.full-width.artdeco-button.artdeco-button--2.artdeco-button--full.artdeco-button--secondary.ember-view","can't find connect button");
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
