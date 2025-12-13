package io.github.zhangrongshun.util;

import java.util.concurrent.TimeUnit;

public class AbcTest {

    public static void main(String[] args) {
        long l = System.nanoTime();
        int i1 = 100000000;
        for (int i = 0; i < i1; i++) {
            String s = PasswordGenerator.generate(4);
//            System.out.println(s);
//            System.gc();
        }
        long l1 = System.nanoTime() - l;
        long seconds = TimeUnit.NANOSECONDS.toSeconds(l1);
        System.out.println(seconds);
        System.out.println(i1 / seconds);
////        String s = generatePassword(5);
////        System.out.println(s);
    }

}
