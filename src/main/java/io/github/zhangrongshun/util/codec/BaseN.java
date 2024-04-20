package io.github.zhangrongshun.util.codec;

import java.math.BigInteger;

public abstract class BaseN {

    private final int radix;

    public BaseN(int radix) {
        this.radix = radix;
    }

    public String encode(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        BigInteger bigInteger = new BigInteger(bytes);
        return bigInteger.toString(radix);
    }

    public byte[] decode(String s) {
        if (s == null) {
            return null;
        }
        BigInteger bigInteger = new BigInteger(s, radix);
        return bigInteger.toByteArray();
    }

}
