package com.nanaios.polygonal_tech.util;

public class MathUtil {
    /// long値をint値に安全に変換する。
    public static int longToInt(long value) {
        if (value > Integer.MAX_VALUE)
            return Integer.MAX_VALUE;
        if (value < Integer.MIN_VALUE)
            return Integer.MIN_VALUE;
        return (int) value;
    }

    /// long値の加算をオーバーフローを起こさずに行う。
    public static long addExact(long a, long b) {
        try {
            return Math.addExact(a, b);
        } catch (ArithmeticException e) {
            if (a > 0 && b > 0)
                return Long.MAX_VALUE;
            if (a < 0 && b < 0)
                return Long.MIN_VALUE;
            throw e;
        }
    }
}
