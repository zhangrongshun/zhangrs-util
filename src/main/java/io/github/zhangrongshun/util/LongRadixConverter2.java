package io.github.zhangrongshun.util;

import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class LongRadixConverter2 {

    private static final boolean compactStrings;

    static {
        boolean flag = false;
        try {
            Field valueField = String.class.getDeclaredField("value");
            flag = byte[].class.equals(valueField.getType());
        } catch (NoSuchFieldException ignored) {
        }
        compactStrings = flag;
    }

    static String toString(char[] digits, int radix, long l, int length) {
        if (compactStrings) {
            return toString1(digits, radix, l, length);
        }
        return toString2(digits, radix, l, length);
    }

    private static String toString2(char[] digits, int radix, long l, int length) {
        int maxLength = 65;
        char[] buf = new char[maxLength];
        int charPos = maxLength - 1;
        boolean negative = (l < 0);
        if (!negative) {
            l = -l;
        }
        while (l <= -radix) {
            buf[charPos--] = digits[(int) (-(l % radix))];
            l = l / radix;
        }
        buf[charPos] = digits[(int) (-l)];
        if (negative) {
            buf[--charPos] = '-';
        }
        int srcLength = maxLength - charPos;
        if (length < 0 || srcLength == length) {
            return new String(buf, charPos, srcLength);
        }
        if (srcLength > length) {
            return new String(buf, maxLength - length, length);
        }
        int repeat = length - srcLength;
        char[] newBuf = new char[length];
        int offset = 0;
        if (buf[0] == '-') {
            newBuf[0] = '-';
            offset = 1;
        }
        int toIndex = repeat + offset;
        Arrays.fill(newBuf, offset, toIndex, (char) digits[0]);
        System.arraycopy(buf, (buf.length - srcLength + offset), newBuf, toIndex, (srcLength - offset));
        return new String(newBuf);
    }

    private static String toString1(char[] digits, int radix, long l, int length) {
        int maxLength = 65;
        byte[] buf = new byte[maxLength];
        int charPos = maxLength - 1;
        boolean negative = (l < 0);
        if (!negative) {
            l = -l;
        }
        while (l <= -radix) {
            buf[charPos--] = (byte) digits[(int) (-(l % radix))];
            l = l / radix;
        }
        buf[charPos] = (byte) digits[(int) (-l)];
        if (negative) {
            buf[--charPos] = '-';
        }
        int srcLength = maxLength - charPos;
        Charset iso88591 = StandardCharsets.ISO_8859_1;
        if (length < 0 || srcLength == length) {
            return new String(buf, charPos, srcLength, iso88591);
        }
        if (srcLength > length) {
            return new String(buf, maxLength - length, length, iso88591);
        }
        int repeat = length - srcLength;
        byte[] newBuf = new byte[length];
        int offset = 0;
        if (buf[0] == '-') {
            newBuf[0] = '-';
            offset = 1;
        }
        int toIndex = repeat + offset;
        Arrays.fill(newBuf, offset, toIndex, (byte) digits[0]);
        System.arraycopy(buf, (buf.length - srcLength + offset), newBuf, toIndex, (srcLength - offset));
        return new String(newBuf, iso88591);
    }

}
