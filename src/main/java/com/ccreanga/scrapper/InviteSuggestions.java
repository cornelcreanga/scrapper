package com.ccreanga.scrapper;

import org.openqa.selenium.WebElement;
import picocli.CommandLine;

import java.util.Map;

import static com.ccreanga.scrapper.Util.sleep;

@CommandLine.Command(name = "invite-suggestions")
public class InviteSuggestions implements Runnable {

    @CommandLine.ParentCommand
    private ParentCommand parent;

    @CommandLine.Option(names = {"-d", "--demo"}, description = "Run the operation in demo mode")
    private boolean demo = true;

    public void run(){
        try {
            Map<String, WebElement> suggestions = SiteManager.getSuggestions(parent.getDriver(),parent.getUser(),parent.getPassword());
            suggestions.forEach((k,v)->{
                if (demo){
                    System.out.println("Suggestion: "+ k);
                }else{
                    v.click();
                    System.out.println("Sending invite to "+k);
                    sleep(2+(int)(Math.random()*4));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

