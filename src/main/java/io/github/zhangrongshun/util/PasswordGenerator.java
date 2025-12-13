package io.github.zhangrongshun.util;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

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
        if (length < 4) {
            throw new IllegalArgumentException("Password length must be at least 5.");
        }
        char[] chars = new char[length];
        MyPredicate predicate = PasswordGenerator::test;
        pad(chars, ALPHABETIC_CHARACTERS, 0, predicate, length);
        pad(chars, isUpperCase(chars[0]) ? LOWERCASE_CHARACTERS : UPPERCASE_CHARACTERS, -1, predicate, length);
        pad(chars, DIGIT_CHARACTERS, -1, predicate, length);
        pad(chars, SPECIAL_CHARACTERS, -1, predicate, length);
        for (int i = 1; i < length; i++) {
            pad(chars, ALL_CHARACTERS, i, predicate, length);
        }
        return new String(chars);
    }

    private static void pad(char[] chars, String str, int targetIndex, MyPredicate predicate, int length) {
        if (targetIndex < 0) {
            for (; ; ) {
                int tempIndex = RANDOM.nextInt(chars.length);
                if (chars[tempIndex] == EMPTY_CHAR) {
                    targetIndex = tempIndex;
                    break;
                }
            }
        } else {
            if (chars[targetIndex] != EMPTY_CHAR) {
                return;
            }
        }
        for (; ; ) {
            int i = RANDOM.nextInt(str.length());
            char target = str.charAt(i);
            char preceding = EMPTY_CHAR;
            char following = EMPTY_CHAR;
            if (targetIndex < length - 1) {
                following = chars[targetIndex + 1];
            }
            if (targetIndex > 0) {
                preceding = chars[targetIndex - 1];
            }
            if (predicate.test(preceding, chars[targetIndex], following, target)) {
                chars[targetIndex] = target;
                break;
            }
        }
    }

    private static boolean test(char preceding, char target, char following, char newValue) {
        if (target != EMPTY_CHAR) {
            return false;
        }
        if (preceding == EMPTY_CHAR && following == EMPTY_CHAR) {
            return true;
        }
        return newValue != preceding && newValue != following;
    }

    @FunctionalInterface
    public interface MyPredicate {
        boolean test(char preceding, char target, char following, char newValue);
    }

    private static boolean isUpperCase(char c) {
        return (c >= 'A') && (c <= 'Z');
    }

    public static void main(String[] args) {
        long l = System.nanoTime();
        int i1 = 100000000;
        for (int i = 0; i < i1; i++) {
            String s = generatePassword(5);
            System.out.println(s);
        }
        long l1 = System.nanoTime() - l;
        long seconds = TimeUnit.NANOSECONDS.toSeconds(l1);
        System.out.println(seconds);
        System.out.println(i1 / seconds);
////        String s = generatePassword(5);
////        System.out.println(s);
    }

}