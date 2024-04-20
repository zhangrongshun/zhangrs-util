package io.github.zhangrongshun.util.codec;

import org.apache.commons.codec.binary.Base32;

public class Base32Coder {

    private static final Base32 BASE32 = new Base32(true);

    private static final Base32 BASE32Hex = new Base32(true);

    public static String encode(byte[] bytes) {
        return encode(bytes, false);
    }

    public static String encode(byte[] bytes, boolean toLowerCase) {
        return encode(bytes, false, toLowerCase);
    }

    public static String encodeHex(byte[] bytes) {
        return encode(bytes, false);
    }

    public static String encodeHex(byte[] bytes, boolean toLowerCase) {
        return encode(bytes, true, toLowerCase);
    }

    public static String encode(byte[] bytes, boolean useHex, boolean toLowerCase) {
        if (bytes == null) {
            return null;
        }
        Base32 base32 = useHex ? BASE32Hex : BASE32;
        String s = base32.encodeAsString(bytes);
        if (toLowerCase) {
            return s.toLowerCase();
        }
        return s;
    }

    public static byte[] decode(String base32String, boolean useHex) {
        if (base32String == null) {
            return null;
        }
        Base32 base32 = useHex ? BASE32Hex : BASE32;
        return base32.decode(base32String);
    }

    public static byte[] decode(String base32String) {
        return decode(base32String, false);
    }

    public static byte[] decodeHex(String base32String) {
        return decode(base32String, true);
    }

}
