package utils;

import org.mindrot.jbcrypt.BCrypt;
import play.Logger;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {
    public static boolean isEmpty(final String value) {
        return ((value == null) || (value.isEmpty()));
    }

    public static String hash(String string){
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(string.getBytes());
            string = new String(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return string;
    }

    private static char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    public static String getMD5Hash(String plain) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] bytes = messageDigest.digest(plain.getBytes("utf-8"));
            String strPass = toHexString(bytes);
            return strPass;
        } catch (UnsupportedEncodingException e) {
            Logger.info("BasicCtr.getMD5Hash: ",e);
            return plain;
        } catch (NoSuchAlgorithmException e) {
            Logger.info("BasicCtr.getMD5Hash: ",e);
            return plain;
        }
    }
    public static String toHexString(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            // look up high nibble char
            sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
            // look up low nibble char
            sb.append(hexChar[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    public static Date stringToDate(String dateString) throws ParseException {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date reformattedStr = myFormat.parse(dateString);
        return  reformattedStr;
    }

    public static boolean checkBcrypt(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    public static String getBcrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(10));
    }
}
