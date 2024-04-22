package io.github.zhangrongshun.util.codec;

import io.github.zhangrongshun.util.codec.exception.DecoderException;

import java.math.BigInteger;

public class BaseNCoder {

    private final int radix;

    private BaseNCoder(int radix) {
        this.radix = radix;
    }

    private String encode(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        BigInteger bigInteger = new BigInteger(bytes);
        return bigInteger.toString(radix);
    }

    private byte[] decode(String s) throws DecoderException {
        if (s == null) {
            return null;
        }
        try {
            BigInteger bigInteger = new BigInteger(s, radix);
            return bigInteger.toByteArray();
        } catch (NumberFormatException e) {
            throw new DecoderException("Illegal string " + s);
        }
    }

    public static byte[] decodeBase16(final String base36Data) throws DecoderException {
        return BaseNSupport.base16.decode(base36Data);
    }

    public static String encodeBase16(final byte[] binaryData) {
        return BaseNSupport.base16.encode(binaryData);
    }

    public static byte[] decodeBase36(final String base36Data) throws DecoderException {
        return BaseNSupport.base36.decode(base36Data);
    }

    public static String encodeBase36(final byte[] binaryData) {
        return BaseNSupport.base36.encode(binaryData);
    }

    static class BaseNSupport {

        private static final BaseNCoder base16 = new BaseNCoder(16);
        private static final BaseNCoder base36 = new BaseNCoder(36);

    }


}
