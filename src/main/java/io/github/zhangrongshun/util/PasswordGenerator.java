package io.github.zhangrongshun.util;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

/**
 * 密码生成工具类
 * 生成符合安全要求的随机密码
 */
public class PasswordGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String LOWERCASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGIT_CHARACTERS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*_?";
    private static final String ALPHABETIC_CHARACTERS = LOWERCASE_CHARACTERS + UPPERCASE_CHARACTERS;
    private static final String ALL_CHARACTERS = LOWERCASE_CHARACTERS + UPPERCASE_CHARACTERS + DIGIT_CHARACTERS + SPECIAL_CHARACTERS;

    public static String generatePassword(int length) {
        if (length < 5) {
            throw new IllegalArgumentException("Password length must be at least 5.");
        }
        char[] chars = new char[length];
        chars[0] = getRandomChar(ALPHABETIC_CHARACTERS);
        pad(chars, isUpperCase(chars[0]) ? getRandomChar(LOWERCASE_CHARACTERS) : getRandomChar(UPPERCASE_CHARACTERS), length);
        pad(chars, getRandomChar(DIGIT_CHARACTERS), length);
        pad(chars, getRandomChar(SPECIAL_CHARACTERS), length);
        for (int i = 1; i < length; i++) {
            if (chars[i] != '\u0000') {
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

    private static void pad(char[] chars, char c, int length) {
        for (; ; ) {
            int i = RANDOM.nextInt(length);
            if (chars[i] == '\u0000') {
                chars[i] = c;
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
        long l = System.nanoTime();
        int i1 = 10;
        for (int i = 0; i < i1; i++) {
            String s = generatePassword(32);
            System.out.println(s);
        }
//        long l1 = System.nanoTime() - l;
//        long seconds = TimeUnit.NANOSECONDS.toSeconds(l1);
//        System.out.println(seconds);
//        System.out.println(i1 / seconds);
////        generatePassword(5);
    }

}