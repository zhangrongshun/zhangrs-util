package io.github.zhangrongshun.util;

public class Base36_1 {

    private static final int radix = 36;
    public static final Base36_1 UPPERCASE = new Base36_1(true);
    public static final Base36_1 LOWERCASE = new Base36_1(false);
    private final char[] digits;

    private Base36_1(boolean uppercase) {
        this.digits = uppercase
                ?
                new char[]{
                        '0', '1', '2', '3', '4', '5',
                        '6', '7', '8', '9', 'A', 'B',
                        'C', 'D', 'E', 'F', 'G', 'H',
                        'I', 'J', 'K', 'L', 'M', 'N',
                        'O', 'P', 'Q', 'R', 'S', 'T',
                        'U', 'V', 'W', 'X', 'Y', 'Z'
                }
                :
                new char[]{
                        '0', '1', '2', '3', '4', '5',
                        '6', '7', '8', '9', 'a', 'b',
                        'c', 'd', 'e', 'f', 'g', 'h',
                        'i', 'j', 'k', 'l', 'm', 'n',
                        'o', 'p', 'q', 'r', 's', 't',
                        'u', 'v', 'w', 'x', 'y', 'z'
                };
    }

    protected char padChar() {
        return digits[0];
    }

    protected char forDigit(int digit) {
        return digits[digit];
    }

}
