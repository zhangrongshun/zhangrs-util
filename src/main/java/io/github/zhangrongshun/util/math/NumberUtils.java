package io.github.zhangrongshun.util.math;

import java.math.BigInteger;

public class NumberUtils {

    public static String convert(String string, int radixFrom, int radixTo) {
        return new BigInteger(string, radixFrom).toString(radixTo);
    }

}
