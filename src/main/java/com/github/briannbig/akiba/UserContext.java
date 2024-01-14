package com.github.briannbig.akiba;


import com.github.briannbig.akiba.entities.User;

public class UserContext {
    private static final ThreadLocal<User> USER_THREAD_LOCAL = new ThreadLocal<>();

    public static void setUserId(User user) {
        USER_THREAD_LOCAL.set(user);
    }

    public static String getUserId() {
        return USER_THREAD_LOCAL.get().getId();
    }

    public static void clear() {
        USER_THREAD_LOCAL.remove();
    }

    public static User getUser() {
        return USER_THREAD_LOCAL.get();
    }

}
