package io.github.zhangrongshun.util;

import java.security.SecureRandom;

/**
 * 密码生成工具类
 * 生成符合安全要求的随机密码
 */
public class PasswordGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();

    // 字符集定义
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*_?";
    private static final String FIRST_CHAR = LOWERCASE + UPPERCASE;
    private static final String ALL_CHARS = LOWERCASE + UPPERCASE + DIGITS + SPECIAL_CHARACTERS;

    public static String generatePassword(int length) {
        if (length < 9) {
            throw new IllegalArgumentException("密码长度不能小于9位");
        }
        char[] chars = new char[length];
        for (int i = 0; i < length; i++) {
            switch (i) {
                case 0:
                    chars[i] = getRandomChar(FIRST_CHAR);
                    break;
                case 1:
                    chars[i] = Character.isUpperCase(chars[0]) ? getRandomChar(LOWERCASE) : getRandomChar(UPPERCASE);
                    break;
                case 2:
                    chars[i] = getRandomChar(DIGITS);
                    break;
                case 3:
                    chars[i] = getRandomChar(SPECIAL_CHARACTERS);
                    break;
                default:
                    chars[i] = getRandomChar(ALL_CHARS);
                    break;
            }
        }
        return new String(chars);
    }

    private static char getRandomChar(String source) {
        int index = RANDOM.nextInt(source.length());
        return source.charAt(index);
    }

    public static void main(String[] args) {
        System.out.println(generatePassword(24));
    }

}