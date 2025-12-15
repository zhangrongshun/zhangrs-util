package io.github.zhangrongshun.util;

import java.util.Arrays;

public class DigitUtils {

    private static final int RADIX_2 = 2;
    private static final int RADIX_36 = 36;
    private static final int RADIX_62 = 62;

    public static String toString(long l, int radix) {
        if (radix < RADIX_2 || radix > RADIX_62) {
            throw new IllegalArgumentException("Invalid radix: " + radix);
        }
        if (radix <= RADIX_36) {
            return Long.toString(l, radix);
        }
        int maxLength = 65;
        char[] buf = new char[maxLength];
        int charPos = maxLength - 1;
        boolean negative = (l < 0);
        if (!negative) {
            l = -l;
        }
        while (l <= -radix) {
            buf[charPos--] = toChar((int) (-(l % radix)), radix);
            l = l / radix;
        }
        buf[charPos] = toChar((int) (-l), radix);
        if (negative) {
            buf[--charPos] = '-';
        }
        return new String(Arrays.copyOfRange(buf, charPos, maxLength));
    }

    private static char toChar(int digit, int radix) {
        if ((radix < RADIX_2) || (radix > RADIX_62)) {
            throw new IllegalArgumentException("Invalid radix: " + radix);
        }
        if ((digit >= radix) || (digit < 0)) {
            throw new IllegalArgumentException("Invalid digit: " + digit);
        }
        if (radix <= RADIX_36) {
            return Character.forDigit(digit, radix);
        }
        if (digit < 10) {
            return (char) ('0' + digit);
        } else if (digit < 36) {
            return (char) ('a' - 10 + digit);
        } else {
            return (char) ('A' - 36 + digit);
        }
    }

    public static void main(String[] args) {
        System.out.println(toString(Long.MAX_VALUE, 62));
        System.out.println(Long.toString(Long.MAX_VALUE, 62));
    }

}