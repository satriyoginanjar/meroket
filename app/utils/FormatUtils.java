package utils;

import java.text.*;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class FormatUtils {
    public static final long  MEGABYTE = 1024 * 1024;
    public static final long  KILOBYTE = 1024 ;
    public static final Locale indonesia = new Locale("ID","id");
    public static final DateFormat defaultDateFormat =  DateFormat.getDateInstance(DateFormat.LONG, indonesia);
    public static final SimpleDateFormat ttsDateFormat = new SimpleDateFormat("ddMMyy");
    public static final DateFormat defaultTimeFormat = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.UK);

    public static String intToStringMonth(int bulan){
        switch (bulan) {
            case 0:
                return "Belum Ditentukan";
            case 1:
                return "Januari";
            case 2:
                return "Februari";
            case 3:
                return "Maret";
            case 4:
                return "April";
            case 5:
                return "Mei";
            case 6:
                return "Juni";
            case 7:
                return "Juli";
            case 8:
                return "Agustus";
            case 9:
                return "September";
            case 10:
                return "Oktober";
            case 11:
                return "November";
            case 12:
                return "Desember";
            default:
                return "";
        }
    }

    public static String formatFileSize(Long ukuran)
    {
        String fileSize="";
        if(ukuran != null) {
            long size=ukuran.longValue();
            if(size < KILOBYTE)
                fileSize=String.valueOf(size);
            else if(size < MEGABYTE)
                fileSize=String.valueOf(size/KILOBYTE) + " KB";
            else
                fileSize=String.valueOf(size/MEGABYTE) + " MB";
        }
        return fileSize;
    }

    public static String formatCurrenciesJuta(Number number) {
        String hasil = "";
        String sufix = "";
        if (number != null) {
            double num = number.doubleValue();
//			double juta = num / 1E6;
            double milyar = num / 1E9;
            double trilyun = num / 1E12;
            // pakai juta atau milyar?
            if (milyar < 1) {
                sufix = " jt";
                num /= 1E6;
            }
            else if(milyar >= 1 && trilyun < 1){
                sufix = " M";
                num /= 1E9;
            }
            else {
                sufix = " T";
                num /= 1E12;
            }
            NumberFormat format = new DecimalFormat("###,###,###,###,###,##0.#");
            hasil = format.format(num);
            //	hasil = "Rp " + hasil; //tanpa Rp
            hasil = hasil.replaceAll(",", "_");
            hasil = hasil.replaceAll("\\.", ",");
            hasil = hasil.replaceAll("_", "\\.");

        }
        return hasil + sufix;
    }

    public static String formatCurrencyRupiah(Number number) {
        String hasil = "";
        if (number != null) {
            NumberFormat format = NumberFormat.getCurrencyInstance(indonesia);
            hasil = format.format(number).replaceAll("Rp", "Rp ");
        }
        return hasil;
    }

    public static String formatDate(Date date) {
        if (date != null) {
//			SimpleDateFormat format = new SimpleDateFormat("d MMM yyyy");
            return defaultDateFormat.format(date);
        }
        return "";
    }

    /**
     * Format tanggal sesuai dengan format tanggal yang ditampilkan oleh JQUERY-Date-Picker
     * @param date tanggal
     * @return string format tanggal
     */
    public static String formatDateToDatePickerView(Date date) {
        if (date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            return formatter.format(date);
        }
        return "";
    }

    /**
     * Format tanggal database dari format tanggal yang ditampilkan oleh JQUERY-Date-Picker
     * @param date tanggal
     * @return objek Date
     */
    public static Date formatDateFromDatePickerView(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            play.Logger.debug("At FormatUtils.formatDateFromDatePickerView -> Tidak dapat melakukan parsing tanggal!");
        }
        return null;
    }

    /**
     * Format tanggal sesuai dengan format tanggal yang ditampilkan oleh JQUERY-DateTime-Picker
     * @param date tanggal
     * @return string format tanggal
     */
    public static String formatDateToDateTimePickerView(Date date) {
        if (date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            return formatter.format(date);
        }
        return "";
    }

    /**
     * Format tanggal database dari format tanggal yang ditampilkan oleh JQUERY-DateTime-Picker
     * @param date tanggal
     * @return objek Date
     */
    public static Date formatDateFromDateTimePickerView(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            play.Logger.debug("At FormatUtils.formatDateFromDateTimePickerView -> Tidak dapat melakukan parsing tanggal!");
        }
        return null;
    }

    public static String formatDateIndWithDay(Date date){
        if(date != null)
            return defaultDateFormat.format(date).replaceFirst(" ", ", ");
        return "";
    }
    public static String formatDateInd(Date date){
        if(date != null)
            return defaultDateFormat.format(date);
        return "";
    }
    public static String formatDateTimeInd(Date date){
        return formatDateInd(date)+" "+formatTime(date);
    }

    public static String formatDateTime(Date date) {
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat("d MMM yyyy HH:mm");
            return format.format(date);
        }
        return "";
    }

    public static String formatDateTimeSecond(Date date) {
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat("d MMM yyyy HH:mm:ss");
            return format.format(date);
        }
        return "";
    }

    public static String formatTime(Date date) {
        if (date != null) {
            return defaultTimeFormat.format(date);
        }
        return "";
    }

    public static String formatDateNoYear(Date date) {
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat("d MMM");
            return format.format(date);
        }
        return "";
    }

    public static String formatDesimal(Number input){
        DecimalFormat df = new DecimalFormat("###,###.### ");
        return df.format(input).replace(',', '.');
    }

    public static String formatNumber(Number number) {
        String hasil = "";
        if (number != null) {
            return NumberFormat.getInstance().format(number);
        }
        return hasil;
    }
    public static long bytesToMeg(long bytes) {
        return bytes / MEGABYTE ;
    }
    public static long bytesToKilo(long bytes) {
        return bytes / KILOBYTE ;
    }
    public static String byteToString(long bytes){
        String byteString=null;
        if (bytes < 1024)
            byteString = String.valueOf(bytes) + " byte";
        else if (bytes < FormatUtils.MEGABYTE)
            byteString = String.valueOf(bytesToKilo(bytes)) + " KB";
        else
            byteString = String.valueOf(bytesToMeg(bytes)) + " MB";
        return byteString;
    }
    public static Integer selisihHari(Date date1, Date date2){
        return Integer.valueOf((int)((date1.getTime()-date2.getTime())/(24 * 3600 * 1000)));
    }

    /**
     * Konversi dari Curreny ke String (Locale Indonesia)
     * @param number
     * @return String
     */
    public static String number2Word(long number) {
        String[] numbers = {"se", "dua ", "tiga ", "empat ", "lima ", "enam ", "tujuh ", "delapan ", "sembilan "};
        String[] levels = {"ribu ", "juta ", "milyar ", "trilyun "};
        StringBuffer result = new StringBuffer();

        String str = String.valueOf(number);
        int mod = str.length() % 3;
        int len = str.length() / 3;
        if(mod>0) len++; else mod = 3;
        int begin = 0;
        int end = mod;
        for(int i=0; i<len; i++) {
            int level = len-i;
            String val = str.substring(begin, end);
            int value = Integer.parseInt(val);
            int length = val.length();
            for(int j=0; j<length; j++) {
                int num = parseInt(val.charAt(j));
                switch(length-j) {
                    case 3:
                        if(num>0) result.append(numbers[num-1]).append("ratus ");
                        break;
                    case 2:
                        if(num>1) result.append(numbers[num-1]).append("puluh ");
                        else if(num==1)
                            result.append(numbers[parseInt(val.charAt(++j))-1]).append("belas ");
                        break;
                    case 1:
                        if(num>1 || (level==2 && value==1)) result.append(numbers[num-1]);
                        else if(num==1) result.append("satu ");
                        break;
                }
            }

            if(level>1 && value>0)
                result.append(levels[level-2]);
            begin = i*3 + mod;
            end += 3;
        }

        return result.toString();
    }
    public static int parseInt(char c) {
        int result = c - 48;
        if(result<0 || result>9) throw new NumberFormatException("For input char: '"+c+"'");
        return result;
    }

    public static String formatDate(Date date1, Date date2) {
        if (date2 == null)
            return formatDate(date1);
        else {
            return formatDate(date1) + " - "+ formatDate(date2);
        }
    }
    public static String getMonthString(int month){
        switch (month){
            case 1: return "Januari";
            case 2: return "Februari";
            case 3: return "Maret";
            case 4: return "April";
            case 5: return "Mei";
            case 6: return "Juni";
            case 7: return "Juli";
            case 8: return "Agustus";
            case 9: return "September";
            case 10: return "Oktober";
            case 11: return "November";
            case 12: return "Desember";
            default: return "N/A";
        }
    }

    public static java.sql.Timestamp getCurrentTimeStamp() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());
    }

    public static java.sql.Timestamp getTimeStampByUnixLong(Long unixTime){
        return new java.sql.Timestamp(unixTime);
    }

    public static java.sql.Timestamp getTimeStampByDate(Date date){
        return new java.sql.Timestamp(date.getTime());
    }

    public static java.sql.Timestamp getTimeStampByUnixTime(int unixTime){
        return new java.sql.Timestamp(unixTime*1000L);
    }

    public static long dateToUnixTime(Date date){
        return date.getTime()/1000;
    }


    public static String getSumberDanaString(int sumberdana){
        switch (sumberdana) {
            case 1:
                return "APBD";
            case 2:
                return "APBN";
            case 3:
                return "PHLN";
            case 4:
                return "PNBP";
            case 5:
                return "APBNP";
            case 6:
                return "APBDP";
            case 7:
                return "BLU";
            case 8:
                return "BLUD";
            case 9:
                return "BUMN";
            case 10:
                return "BUMD";
            default:
                return "Lainnya";
        }
    }

    public static String getIndonesianDateString (Date date) {
        SimpleDateFormat formatIncoming = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        SimpleDateFormat formatOutgoing = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        TimeZone tz = TimeZone.getTimeZone("Asia/Jakarta");
        formatOutgoing.setTimeZone(tz);
        try{
            return formatOutgoing.format(formatIncoming.parse(date.toString()));
        }catch (Exception e){
            return "";
        }

    }
}
