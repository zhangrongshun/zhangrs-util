package io.github.zhangrongshun.util;

public class Amn {

    public static void main(String[] args) {
        long l = 1000000000000L;
        int count = 0;

        for (int i = 0; i < 100; i++) {
            long l1 = a1(l);
            long l2 = a2(l);
            boolean x = l1 < l2;
            System.out.println(x);
            if (x) {
                count++;
            }
        }
        System.out.println(count);

    }

    private static long a2(long l) {
        long l3 = System.nanoTime();
        byte c1 = 65;
        for (long i = 0; i < l; i++) {
//            char b = (char) (c1 + (i & 0x7F));
            char b = (char) (c1 + 1);
        }
        long l4 = System.nanoTime() - l3;
        System.out.println(l4);
        return l4;
    }

    private static long a1(long l) {
        long l1 = System.nanoTime();
        char c = 'A';
        for (long i = 0; i < l; i++) {
//            byte b = (byte) (c + (i & 0xFF));
            byte b = (byte) (c + 1);
        }
        long l2 = System.nanoTime() - l1;
        System.out.println(l2);
        return l2;
    }

}
