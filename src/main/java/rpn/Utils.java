package rpn;

import java.text.DecimalFormat;

/**
 * @author: Peng Cheng
 * @description: the utils.
 * @since: 2020/10/13 23:34
 */
public class Utils {

    /**
     * judge the element is real number or not.
     *
     * @param element single element
     */
    public static boolean isRealNumber(String element) {
        try {
            Double.parseDouble(element);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * format for presentation.
     *
     * @param number single element
     */
    public static String formatDouble(Double number) {
        DecimalFormat decimalFormat = new DecimalFormat("0.##########");
        return decimalFormat.format(number);
    }
}