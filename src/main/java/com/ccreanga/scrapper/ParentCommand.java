package com.ccreanga.scrapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.WebDriverManagerException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import picocli.CommandLine;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

@CommandLine.Command(name = "linkedin",
        subcommands = {InviteSuggestions.class,
                ListSuggestions.class})
public class ParentCommand implements Runnable {

    public static final Logger log = getLogger(lookup().lookupClass());

    @CommandLine.Option(names = {"-u", "--user"}, required = true, description = "user")
    protected String user;

    @CommandLine.Option(names = {"-p", "--password"}, required = true, description = "password")
    protected String password;

    protected WebDriver driver;

    @Override
    public void run() {
        try {
            WebDriverManager.chromedriver().setup();
        }catch (WebDriverManagerException e){
            log.info("can't setup the chrome driver, the error is {}", e.getMessage());
        }
        try{
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }catch(IllegalStateException e){
            log.info("can't instantiate chrome driver, the error is {}", e.getMessage());
        }
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public static void main(String[] args) {
        CommandLine commandLine = new CommandLine(new ParentCommand());
        if (args.length == 0) {
            commandLine.usage(System.out);
        } else
            commandLine.parseWithHandler(new CommandLine.RunAll(), args);
    }
}
