package com.wolf.nniroula.creditrecorder.utils;

import java.text.DecimalFormat;

/**
 * Created by root on 2/27/17.
 */

public class FormatLargeNumber {

    public static String formattedNumber(Double number) {
        // Prepare format to apply
        DecimalFormat format = new DecimalFormat();
        format.setGroupingUsed(true);
        format.setParseBigDecimal(true);
        format.setMaximumIntegerDigits(11);
        format.setMaximumFractionDigits(2);

        // Formats the number

        String formattedText = format.format(number);
        return formattedText;
    }
}
