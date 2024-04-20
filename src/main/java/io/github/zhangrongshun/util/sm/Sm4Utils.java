package io.github.zhangrongshun.util.sm;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;

public class Sm4Utils extends SmBase {

    public static final String ALGORITHM_NAME = "SM4";
    public static final String ALGORITHM_NAME_ECB_PADDING = "SM4/ECB/PKCS7Padding";

    private static Cipher generateEcbCipher(int mode, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance(Sm4Utils.ALGORITHM_NAME_ECB_PADDING, "BC");
        Key sm4Key = new SecretKeySpec(key, ALGORITHM_NAME);
        cipher.init(mode, sm4Key);
        return cipher;
    }

    public static byte[] encryptEcbPadding(byte[] data, byte[] key) throws Exception {
        Cipher cipher = generateEcbCipher(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    public static byte[] decryptEcbPadding(byte[] cipherText, byte[] key) throws Exception {
        Cipher cipher = generateEcbCipher(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(cipherText);
    }

    public static void main(String[] args) throws Exception {
        byte[] bytes = Sm4Utils.encryptEcbPadding("111111111111111".getBytes(StandardCharsets.UTF_8), "bbbbbbbbbbbbbbbb".getBytes(StandardCharsets.UTF_8));
        String s = Base64.encodeBase64String(bytes);
        System.out.println(s);
        System.out.println(Hex.encodeHexString(bytes));
        System.out.println(s.length());
        byte[] bytes1 = Sm4Utils.decryptEcbPadding(Base64.decodeBase64(s), "bbbbbbbbbbbbbbbb".getBytes(StandardCharsets.UTF_8));
        System.out.println(new String(bytes1, StandardCharsets.UTF_8));
        byte[] hash = Sm3Utils.hash(bytes);
        String s1 = new Base32(true, (byte) 'Z').encodeToString(hash).toLowerCase();
        System.out.println(s1);
        System.out.println(Base64.encodeBase64String(hash));
        String x1 = Hex.encodeHexString(hash);
        System.out.println(x1);
//        long l = Long.parseLong(x1, 16);
//        System.out.println(l);
        String x = new String(new byte[]{});
        System.out.println(x);
        System.out.println(x.length());
    }

}
