package io.github.zhangrongshun.util.math;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.ArrayList;

public class NumberUtils {

    public static String convert(String string, int radixFrom, int radixTo) {
        return new BigInteger(string, radixFrom).toString(radixTo);
    }

    public static void main(String[] args) {
//        String convert = convert("-1000000000", 2, 32);
//        String format = StringUtils.leftPad(convert, 10, '0');
//        System.out.println(format);
//        System.out.println(convert);
        ArrayList<String> strings = Lists.newArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9");
        System.out.println(strings);
    }

}
