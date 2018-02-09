package rpn.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * some common utils
 *
 * @author tracy.
 * @create 2018-02-06 19:51
 **/
public class NumberUtils {

    private static final Pattern NUM_PATTERN = Pattern.compile("[0-9]*");

    /**
     * judge the input is number or not
     *
     * @param str input string
     * @return if str is number return true else false
     */
    public static boolean isNumeric(String str) {
        Matcher isNum = NUM_PATTERN.matcher(str);
        return isNum.matches();
    }

    /**
     * format the input value user ROUND_DOWN model
     *
     * @param value  input value
     * @param places how many value need leave
     * @return new value
     */
    public static String formatDouble(BigDecimal value, int places) {
        //if value is int,
        if (new BigDecimal(value.intValue()).compareTo(value) == 0) {
            return String.valueOf(value.intValue());
        }
        DecimalFormat formatter = new DecimalFormat("0.##########");
        return formatter.format(value.setScale(places, BigDecimal.ROUND_DOWN));
    }
}
