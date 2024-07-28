package io.taraxacum.common.util;

public class MathUtil {

    public static double getBig(long a, long b, long c) {
        return (-1 * b + Math.pow(b * b - 4 * a * c, 0.5)) / 2;
    }

    public static double getSmall(long a, long b, long c) {
        return (-1 * b - Math.pow(b * b - 4 * a * c, 0.5)) / 2;
    }

    public static double getLog(double a, double b) {
        return Math.log(b) / Math.log(a);
    }
}
