package io.github.zhangrongshun.util.codec;

import java.math.BigInteger;

public class BaseN {

    private final int radix;

    private BaseN(int radix) {
        this.radix = radix;
    }

    private String encode(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        BigInteger bigInteger = new BigInteger(bytes);
        return bigInteger.toString(radix);
    }

    private byte[] decode(String s) {
        if (s == null) {
            return null;
        }
        BigInteger bigInteger = new BigInteger(s, radix);
        return bigInteger.toByteArray();
    }

    public static byte[] decodeBase16(final String base36Data) {
        return BaseNSupport.base16.decode(base36Data);
    }

    public static String encodeBase16(final byte[] binaryData) {
        return BaseNSupport.base16.encode(binaryData);
    }

    public static byte[] decodeBase36(final String base36Data) {
        return BaseNSupport.base36.decode(base36Data);
    }

    public static String encodeBase36(final byte[] binaryData) {
        return BaseNSupport.base36.encode(binaryData);
    }

    static class BaseNSupport {

        private static final BaseN base16 = new BaseN(16);
        private static final BaseN base36 = new BaseN(36);

    }


}
