package com.ccreanga.scrapper;

public class Util {

    public static void sleep(int seconds){
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {//ignored
        }
    }

}
