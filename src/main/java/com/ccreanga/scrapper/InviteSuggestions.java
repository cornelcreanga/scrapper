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
                    try {
                        v.click();
                    }catch(Exception e){
                        System.out.println("Can't click on "+k+" - it might be outside of the visible panel");
                        return;
                    }
                    int sleep = 2+(int)(Math.random()*4);
                    System.out.println("Sent invite to " +k + " sleeping " + sleep + " seconds");
                    sleep(sleep);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

