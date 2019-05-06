package com.ccreanga.scrapper;

import org.openqa.selenium.WebElement;
import picocli.CommandLine;

import java.util.Map;

@CommandLine.Command(name = "list-suggestions")
public class ListSuggestions implements Runnable {

    @CommandLine.ParentCommand
    private ParentCommand parent;

    public void run(){
        try {
            Map<String, WebElement> suggestions = SiteManager.getSuggestions(parent.getDriver(),parent.getUser(),parent.getPassword());
            suggestions.keySet().forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
