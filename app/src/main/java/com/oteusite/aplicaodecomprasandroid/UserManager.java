package com.oteusite.aplicaodecomprasandroid;

public class UserManager {
    private static User loggedInUser;

    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void logout() {
        loggedInUser = null;
    }
}
