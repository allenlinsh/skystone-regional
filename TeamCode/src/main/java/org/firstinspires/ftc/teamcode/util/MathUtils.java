package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.util.Range;

public class MathUtils {
    /**
     * Round a decimal number to {roundTo} digits
     * @param value desired value to be calculated
     * @param roundTo number of digits to be rounded to
     * @return decimal number rounded to the desired digits
     */
    public static double round(double value, int roundTo) {
        return Double.valueOf(String.format("%." + roundTo + "f", value));
    }

    /**
     * Clip a number to a range including its sign
     * @param value desired value to be calculated
     * @param minimum the minimum value
     * @param maximum the maximum value
     * @return a number clipped within the range and multiply by its sign (+/-)
     */
    public static double clip(double value, double minimum, double maximum) {
        return Math.signum(value) * Range.clip(Math.abs(value), minimum, maximum);
    }
}
