package io.github.zhangrongshun.util;

import java.security.SecureRandom;

public class PasswordGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String LOWERCASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGIT_CHARACTERS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*_?";
    private static final String ALPHABETIC_CHARACTERS = LOWERCASE_CHARACTERS + UPPERCASE_CHARACTERS;
    private static final String ALL_CHARACTERS = LOWERCASE_CHARACTERS + UPPERCASE_CHARACTERS + DIGIT_CHARACTERS + SPECIAL_CHARACTERS;
    private static final char EMPTY_CHAR = '\u0000';

    public static String generatePassword(int length) {
        if (length < 5) {
            throw new IllegalArgumentException("Password length must be at least 5.");
        }
        char[] chars = new char[length];
        chars[0] = getRandomChar(ALPHABETIC_CHARACTERS);
        pad(chars, isUpperCase(chars[0]) ? LOWERCASE_CHARACTERS : UPPERCASE_CHARACTERS);
        pad(chars, DIGIT_CHARACTERS);
        pad(chars, SPECIAL_CHARACTERS);
        for (int i = 1; i < length; i++) {
            if (chars[i] != EMPTY_CHAR) {
                continue;
            }
            for (; ; ) {
                char randomChar = getRandomChar(ALL_CHARACTERS);
                if (randomChar != chars[i - 1] && ((i < length - 1 && randomChar != chars[i + 1]) || i == length - 1)) {
                    chars[i] = randomChar;
                    break;
                }
            }
        }
        return new String(chars);
    }

    private static void pad(char[] chars, String str) {
        for (; ; ) {
            int i1 = RANDOM.nextInt(chars.length);
            if (chars[i1] == EMPTY_CHAR) {
                int i = RANDOM.nextInt(str.length());
                chars[i1] = str.charAt(i);
                break;
            }
        }
    }

    private static boolean isUpperCase(char c) {
        return (c >= 'A') && (c <= 'Z');
    }

    private static char getRandomChar(String source) {
        int index = RANDOM.nextInt(source.length());
        return source.charAt(index);
    }

    public static void main(String[] args) {
//        long l = System.nanoTime();
//        int i1 = 10000000;
//        for (int i = 0; i < i1; i++) {
//            String s = generatePassword(100);
////            System.out.println(s);
//        }
//        long l1 = System.nanoTime() - l;
//        long seconds = TimeUnit.NANOSECONDS.toSeconds(l1);
//        System.out.println(seconds);
//        System.out.println(i1 / seconds);
        String s = generatePassword(50);
        System.out.println(s);
    }

}