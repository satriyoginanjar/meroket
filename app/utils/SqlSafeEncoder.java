package utils;

public class SqlSafeEncoder {

    public static String enc(String param) {
        try {
            return org.postgresql.core.Utils.escapeLiteral(null, param, true).toString();
        } catch (Exception e) {
            return "";
        }
    }
}
