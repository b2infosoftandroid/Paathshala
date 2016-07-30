package com.b2infosoft.paathshala.app;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rajesh on 7/14/2016.
 */
public class Valid {

    static public boolean isEnrollmentNumber(String enroll) {
        if (enroll.length() >= 5 && enroll.length() <= 8) {
            return true;
        }
        return false;
    }

    static public boolean isMobileNumber(String mobile) {
        boolean check = false;
        if (!Pattern.matches("[a-zA-Z]+", mobile)) {
            if (mobile.length() != 10) {
                check = false;
            } else {
                check = true;
            }
        } else {
            check = false;
        }
        return check;
    }

    static public boolean isPassword(String password) {
        boolean check = false;
        if (password.length() == 0) {
            check = false;
        } else {
            check = true;
        }
        return check;
    }

    static public boolean isPasswordConfirm(String s, String s1) {
        return s.equals(s1);
    }

    static public boolean isName(String name) {
        Pattern pattern = Pattern.compile(new String("^[a-zA-Z\\s]*$"));
        Matcher matcher = pattern.matcher(name);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }
    static public boolean isSubject(String subject){
        boolean check = false;
        if (subject.length() == 0) {
            check = false;
        } else {
            check = true;
        }
        return check;
    }
    static public boolean isMessage(String message){
        boolean check = false;
        if (message.length() == 0) {
            check = false;
        } else {
            check = true;
        }
        return check;
    }

}
