package nl.davebeerensdesigns.hosting_management.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidMetaData {
    public static boolean isValidMetaKey(String metakey) {
        String regex = "^[a-zA-Z_]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(metakey);
        return matcher.matches();
    }
    public static boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}