package io.github.zhangrongshun.util.codec;

public class Base36 extends BaseN {

    private Base36() {
        super(36);
    }

    public static byte[] decodeBase36(final String base36Data) {
        return new Base36().decode(base36Data);
    }

    public static String encodeBase36(final byte[] binaryData) {
        return new Base36().encode(binaryData);
    }
}
