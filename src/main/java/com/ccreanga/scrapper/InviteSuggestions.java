package com.ccreanga.scrapper;

import org.openqa.selenium.WebElement;
import picocli.CommandLine;

import java.util.Map;

@CommandLine.Command(name = "invite-suggestions")
public class InviteSuggestions implements Runnable {

    @CommandLine.ParentCommand
    private ParentCommand parent;

    public void run(){
        try {
            Map<String, WebElement> suggestions = SiteManager.getSuggestions(parent.getDriver(),parent.getUser(),parent.getPassword());
            suggestions.forEach((k,v)->{
                System.out.println("cucu");
                //v.click();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

