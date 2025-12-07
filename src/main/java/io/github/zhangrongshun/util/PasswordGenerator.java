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
        if (length < 1) {
            throw new IllegalArgumentException("密码长度不能小于1");
        }
        char[] chars = new char[length];
        chars[0] = getRandomChar(ALPHABETIC_CHARACTERS);
        if (length > 1) {
            chars[1] = Character.isUpperCase(chars[0]) ? getRandomChar(LOWERCASE_CHARACTERS) : getRandomChar(UPPERCASE_CHARACTERS);
        }
        if (length > 2) {
            chars[2] = getRandomChar(DIGIT_CHARACTERS);
        }
        if (length > 3) {
            chars[3] = getRandomChar(SPECIAL_CHARACTERS);
        }
        if (length > 4) {
            for (int i = 4; i < length; i++) {
                for (; ; ) {
                    char randomChar = getRandomChar(ALL_CHARACTERS);
                    if (randomChar != chars[i - 1]) {
                        chars[i] = randomChar;
                        break;
                    }
                }
            }
        }
        return new String(chars);
    }

    private static char getRandomChar(String source) {
        int index = RANDOM.nextInt(source.length());
        return source.charAt(index);
    }

    public static void main(String[] args) {
        long l = System.nanoTime();
        int i1 = 1000000000;
        for (int i = 0; i < i1; i++) {
            generatePassword(10);
        }
        long l1 = System.nanoTime() - l;
        long seconds = TimeUnit.NANOSECONDS.toSeconds(l1);
        System.out.println(seconds);
        System.out.println(i1 / seconds);
//        generatePassword(5);
    }

}