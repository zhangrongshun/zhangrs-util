package io.github.zhangrongshun.util.codec;

public class Base32Coder {

    private static final org.apache.commons.codec.binary.Base32 BASE32 = new org.apache.commons.codec.binary.Base32(true);

    public static String encode(byte[] bytes) {
        return encode(bytes, false);
    }

    public static String encode(byte[] bytes, boolean toLowerCase) {
        if (bytes == null) {
            return null;
        }
        String s = BASE32.encodeAsString(bytes);
        if (toLowerCase) {
            return s.toLowerCase();
        }
        return s;
    }

    public static byte[] decode(String base32String) {
        return BASE32.decode(base32String);
    }

}
