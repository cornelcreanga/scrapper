package com.ccreanga.scrapper;

import org.openqa.selenium.WebDriver;
import picocli.CommandLine;

import java.util.logging.*;

@CommandLine.Command(name = "linkedin",
        subcommands = {InviteSuggestions.class,
                ListSuggestions.class})
public class ParentCommand implements Runnable {

    public static final Logger log = Logger.getLogger("log");

    @CommandLine.Option(names = {"-u", "--user"}, required = true, description = "user")
    protected String user;

    @CommandLine.Option(names = {"-p", "--password"}, required = true, description = "password")
    protected String password;

    protected WebDriver driver;

    @Override
    public void run() {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                String message = record.getMessage();
                if (message.endsWith("\\"))
                    return message.substring(0,message.length()-1);
                return record.getMessage() + "\n";
            }
        });
        consoleHandler.setLevel(Level.INFO);
        log.setUseParentHandlers(false);
        log.addHandler(consoleHandler);
        log.setLevel(Level.INFO);

        SeleniumUtils.locateChromeDriver(null);
        if (((user==null) || (password==null))){
            user  = System.getenv("LINKEDIN_USER");
            password = System.getenv("LINKEDIN_PASSWD");
        }
        if ((user==null) || (password==null)){
            log.info("user/passwd should be configured as env variables (LINKEDIN_USER/LINKEDIN_PASSWD) or passed as arguments");
            System.exit(1);
        }
        driver = SeleniumUtils.createWebDriver();
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
