package io.github.zhangrongshun.util.codec;

import io.github.zhangrongshun.util.codec.exception.BaseNDecoderException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class Test {

    public static void main(String[] args) throws DecoderException, BaseNDecoderException {
//        String s = BaseN.encodeBase32("10".getBytes(StandardCharsets.UTF_8));
//        System.out.println(s);
//        byte[] bytes = BaseN.decodeBase32(s);
//        System.out.println(new String(bytes, StandardCharsets.UTF_8));
////        BigInteger bigInteger = new BigInteger("31x");
////        System.out.println(bigInteger.toString(32));
//        String s = BaseN.encodeBase16("10000".getBytes(StandardCharsets.UTF_8));
//        System.out.println(s);
//        String s1 = Hex.encodeHexString("10000".getBytes(StandardCharsets.UTF_8));
//        System.out.println(s1);
        char[] chars = Hex.encodeHex("10".getBytes(StandardCharsets.UTF_8));
        System.out.println(new String(chars));
        String s1 = BaseNCoder.encodeBase16("10".getBytes(StandardCharsets.UTF_8));
        System.out.println(s1);
        byte[] bytes1 = Hex.decodeHex(s1);
        System.out.println(new String(bytes1, StandardCharsets.UTF_8));
        byte[] bytes2 = BaseNCoder.decodeBase16(s1);
        System.out.println(new String(bytes2, StandardCharsets.UTF_8));
        BigInteger bigInteger = new BigInteger("999999999999999999999999999999999");
        System.out.println(bigInteger.toString(36));
        BigInteger bigInteger1 = new BigInteger("zzzzzz", 36);
        System.out.println(bigInteger1.toString(10));
        System.out.println(bigInteger1.toString(10).length());
        byte[] bytes3 = Base64.decodeBase64("自然");
        System.out.println(new String(bytes3, StandardCharsets.UTF_8));
//        byte[] bytes4 = Hex.decodeHex("zz");
//        System.out.println(new String(bytes4, StandardCharsets.UTF_8));
        byte[] bytes = BaseNCoder.decodeBase16("zz");
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
    }

}
