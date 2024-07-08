package stronghold.model.utils;

public class StringParser {
    public static String removeQuotes(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        if (str.charAt(0) == '"' && str.charAt(str.length() - 1) == '"') {
            return str.substring(1, str.length() - 1);
        }
        return str;
    }

}