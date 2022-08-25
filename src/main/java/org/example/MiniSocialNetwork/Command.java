package org.example.MiniSocialNetwork;

public class Command {

    public static void home(){
        System.out.println("For login press 1");
        System.out.println("For registration press 2");
    }

    public static void userHome(){
        System.out.println("For see all articles press 1");
        System.out.println("For see your articles press 2");
        System.out.println("For add new article press 3");
        System.out.println("For logout press 4");
    }
}