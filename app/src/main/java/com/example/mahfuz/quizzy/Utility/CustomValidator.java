package com.example.mahfuz.quizzy.Utility;

import java.util.regex.Pattern;

public class CustomValidator {

    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static boolean isEmailValid(String s) {
        if (s == null || s.equals("")) {
            return false;
        }
        else if (Pattern.matches(EMAIL_PATTERN, s)) {
            return true;
        }
        return false;
    }

    public static  boolean isPasswordValid(String password) {
        if (password == null || password.equals("") || password.length()<6) {
            return false;
        }
        return true;
    }

    public static  boolean isFullNameValid(String fullName) {
        if (fullName == null || fullName.equals("") || fullName.length()<3) {
            return false;
        }
        return true;
    }

}
