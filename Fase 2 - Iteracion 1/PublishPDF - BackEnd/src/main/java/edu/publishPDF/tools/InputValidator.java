package edu.publishPDF.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {

    private static Pattern textPattern = Pattern.compile("^[\\w \\n]*$");
    private static Pattern numPattern = Pattern.compile("^[\\d]+$");
    
    public static boolean isValidText(String s) {
        Matcher m = textPattern.matcher(s);
        return m.find();
    }

    public static boolean isUnsignedInt(String num) {
        Matcher m = numPattern.matcher(num);
        return m.find();
    }
}
