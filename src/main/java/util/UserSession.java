package util;

import model.User;

public final class UserSession {
    private static User currentUser;

    private UserSession() {}

    public static User getCurrentUser() { return currentUser; }
    public static void setCurrentUser(User user) { currentUser = user; }
    public static boolean isLoggedIn() { return currentUser != null; }
    public static boolean isAdmin() { return currentUser != null && currentUser.isAdmin(); }
    public static void clear() { currentUser = null; }
}