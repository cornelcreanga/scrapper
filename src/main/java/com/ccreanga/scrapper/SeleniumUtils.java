package com.ccreanga.scrapper;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;

public class SeleniumUtils {

    public static void locateChromeDriver() {
        locateChromeDriver(null);
    }

        public static void locateChromeDriver(String driverLocation) {
        String chromeDriver = "chromedriver";
        String os = System.getProperty("os.name").toLowerCase();
        if (os.startsWith("windows"))
            chromeDriver += ".exe";
        else if (os.startsWith("linux"))
            chromeDriver += "_linux";
        if (driverLocation == null) {
            System.out.println("chrome driver location was not provided, looking into " +
                System.getProperty("user.home") + " for a file named " + chromeDriver);
            File file = new File(System.getProperty("user.home") + File.separator + chromeDriver);
            if (!file.exists() || !file.isFile()) {
                System.out.println("can't find chromedriver, exiting.");
                System.exit(1);
            }
            driverLocation = file.getAbsolutePath();
        }
        System.setProperty("webdriver.chrome.driver", driverLocation);
    }

    public static WebDriver createWebDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
            "start-maximized",
            "forced-maximize-mode");

        LoggingPreferences pref = new LoggingPreferences();
        pref.enable(LogType.BROWSER, Level.ALL);

        options.setCapability(CapabilityType.LOGGING_PREFS, pref);

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
        return driver;
    }
}
