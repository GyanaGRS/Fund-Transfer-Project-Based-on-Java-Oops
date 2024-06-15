package com.pay.chikun.validation;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.pay.chikun.bean.User;

public class ValidateUser {
    private static Matcher matcher;     //use to match any pattern
    private static Pattern pattern;     //use to make any pattern (like email pattern)
    private static final String EMAIL_PATTERN= "^(?=.{1,64}@)[\\\\p{L}0-9_-]+(\\\\.[\\\\p{L}0-9_-]+)*@\" \n" +
            "        + \"[^-][\\\\p{L}0-9-]+(\\\\.[\\\\p{L}0-9-]+)*(\\\\.[\\\\p{L}]{2,})$";
    public static boolean checkLength(int length, String text, boolean lengthEquals) {
        if (lengthEquals) {
            if(text.length()== length && text!= null) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            if(text.length()> length && text!= null) {
                return true;
            }
            else {
                return false;
            }
        }

    }
    //check valid email method
    public static boolean validateEmail(String email) {
        pattern= pattern.compile(EMAIL_PATTERN);
        matcher= pattern.matcher(email);

        return matcher.matches();
    }
    //security pin generation
    public static boolean verifyPin(int pin, User user) {
        if(pin== user.getAccountPin())
                return true;
        else
            return false;
    }
    public static boolean isNotNull(String txt) {
        return txt != null &&  txt.trim().length()> 0 ? true : false;
    }
    //validating password with retype password
    public static boolean validatePassword(String pass) {
        if (pass != null  &&  pass.length()> 3) { //as above here we can also use ternary operator rather than if else
            return true;
        }
        else
            return false;
    }
    public static boolean haveSpace(String username) {
        boolean checkSpace= false;
        int f=0;
        for (int i=0; i< username.length(); i++) {
            if(username.contains(" ")) {
                f = 1;
                checkSpace = true;
            }
        }
        if(f== 1) {
            return checkSpace;
        }
        else
            return checkSpace;
    }
    public static boolean validateMaxMobile(String mobile) {
        if (mobile!= null  &&  mobile.length()> 10) {
            return true;
        }
        return false;
    }
    public static boolean validateMinMobile(String mobile) {
        if (mobile!= null  &&  mobile.length()< 10) {
            return true;
        }
        return false;
    }
}
