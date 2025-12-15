package io.github.zhangrongshun.util;

import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public enum LongRadixConverter {

    BASE16_LOWERCASE_PRIORITY(16, CasePriority.LOWERCASE_PRIORITY),
    BASE16_UPPERCASE_PRIORITY(16, CasePriority.UPPERCASE_PRIORITY),
    BASE36_LOWERCASE_PRIORITY(36, CasePriority.LOWERCASE_PRIORITY),
    BASE36_UPPERCASE_PRIORITY(36, CasePriority.UPPERCASE_PRIORITY),
    BASE62_LOWERCASE_PRIORITY(62, CasePriority.LOWERCASE_PRIORITY),
    BASE62_UPPERCASE_PRIORITY(62, CasePriority.UPPERCASE_PRIORITY);

    private final int radix;
    private final byte[] digits;
    private final int mode;
    private final int maxLength;
    private static final char CASE_MASK = 0x20;
    private static final int BYTE_TO_CHAR_MASK = 0xFF;

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

    LongRadixConverter(int radix, int mode) {
        if (radix < 2 || radix > 62) {
            throw new IllegalArgumentException("Invalid radix: " + radix);
        }
        if (mode != CasePriority.LOWERCASE_PRIORITY && mode != CasePriority.UPPERCASE_PRIORITY) {
            throw new IllegalArgumentException("Invalid mode: " + mode);
        }
        this.radix = radix;
        this.digits = initDigits();
        this.mode = mode;
        this.maxLength = (int) Math.ceil(Math.log(Long.MAX_VALUE) / Math.log(radix)) + 1;
    }

    private byte[] initDigits() {
        byte[] digits = new byte[radix];
        for (int i = 0; i < radix; i++) {
            digits[i] = forDigit(i);
        }
        return digits;
    }

    public String toString(long l, int length) {
        if (compactStrings) {
            return toString1(l, length);
        }
        return toString2(l, length);
    }

    private String toString2(long l, int length) {
        char[] buf = new char[maxLength];
        int charPos = maxLength - 1;
        boolean negative = (l < 0);
        if (!negative) {
            l = -l;
        }
        while (l <= -radix) {
            buf[charPos--] = (char) (digits[(int) (-(l % radix))] & BYTE_TO_CHAR_MASK);
            l = l / radix;
        }
        buf[charPos] = (char) (digits[(int) (-l)] & BYTE_TO_CHAR_MASK);
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

    private String toString1(long l, int length) {
        byte[] buf = new byte[maxLength];
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
        Arrays.fill(newBuf, offset, toIndex, digits[0]);
        System.arraycopy(buf, (buf.length - srcLength + offset), newBuf, toIndex, (srcLength - offset));
        return new String(newBuf, iso88591);
    }

    public String toString(long i) {
        return toString(i, -1);
    }

    private byte forDigit(int digit) {
        if (digit < 10) {
            return (byte) (digit + '0');
        }
        char firstLetter = mode == CasePriority.UPPERCASE_PRIORITY ? 'A' : 'a';
        if (digit < 36) {
            return (byte) (digit - 10 + firstLetter);
        }
        return (byte) (digit - 36 + (firstLetter ^ CASE_MASK));
    }

    static class CasePriority {
        public static final int LOWERCASE_PRIORITY = 1;
        public static final int UPPERCASE_PRIORITY = 2;
    }

}
