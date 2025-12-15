package io.github.zhangrongshun.util;

import java.util.concurrent.TimeUnit;
import java.util.function.LongConsumer;

public class M {

    private static final int strLength = 7;

    public static void main(String[] args) {
////        System.out.println(Base36.toString(Long.MAX_VALUE, 15));
////        System.out.println(Base36_2.toString(Long.MAX_VALUE, 15));
//
//
////        long i1 = 1L;
//        System.out.println("------------------------------");
//        extracted(i -> Base36.UPPERCASE.toString(i, 15));
//        System.out.println("------------------------------");
//        extracted(i -> Base36_3.UPPERCASE.toString(i, 15));
//        System.out.println("------------------------------");
//        extracted(i -> Base36.LOWERCASE.toString(i, 15));
//        System.out.println("------------------------------");
//        extracted(i -> Base36_3.LOWERCASE.toString(i, 15));
//        System.out.println("------------------------------");
//        String string = Base16.UPPERCASE.toString(Long.MIN_VALUE);
//        System.out.println(string);
//        String string1 = Long.toString(10010, 16);
//        System.out.println(string1);
        System.out.println("------------------------------");
        extracted(M::getBase161);
        System.out.println("------------------------------");
        extracted(M::getBase16);
        System.out.println("------------------------------");
        extracted(M::getBase16);
        System.out.println("------------------------------");
        extracted(M::getBase161);
        System.out.println("------------------------------");
//        System.out.println(Long.toString(1000000000L, 36));
    }

    private static void getBase161(long i) {
//        return Base36.UPPERCASE.toString(i, 6);
//        return "";
//        String string = LongRadixConverter.BASE36_UPPERCASE_PRIORITY.toString(i, strLength);
//        String string = LongRadixConverter2.BASE36_LOWERCASE_PRIORITY.toString(i);
//        System.out.println(string);
//        String string = LongRadixConverter.BASE36_LOWERCASE_PRIORITY.toString(i);
        String string = LongRadixConverter.BASE36_LOWERCASE_PRIORITY.toString(i);
//        System.out.println(string);
//        java.lang.StringLanguage.toUpperCase(string);
    }

    private static void getBase16(long i) {
//        String upperCase = Ascii.toUpperCase(Long.toString(i, 36));
        String upperCase = Long.toString(i, 36);
//        System.out.println(upperCase);
//        System.out.println(upperCase);
//        String s = StringUtils.leftPad(upperCase, strLength, '0');
//        System.out.println(s);
//        return s;
    }

    private static void extracted(LongConsumer consumer) {
//        try (ExecutorService executorService = Executors.newFixedThreadPool(10)) {
        long i1 = 1000000000L;
        long l = System.nanoTime();
        for (long i = -i1; i <= i1; i++) {
//        for (long i = i1; i < i1 + 1; i++) {
//        for (long i = -i1; i < -i1 + 1; i++) {
//                final long i2 = i;
//                executorService.execute(() -> consumer.accept(i2));
            consumer.accept(i);
        }
        long l1 = System.nanoTime() - l;
        long seconds = TimeUnit.NANOSECONDS.toSeconds(l1);
        if (seconds > 0) {
            long l2 = i1 / seconds;
            System.out.println(seconds);
            System.out.println(l2);
        }
        System.out.println(l1);
//        }
    }


}
