package io.github.zhangrongshun.util.sm;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

public class Sm3Utils extends SmBase {

    public static byte[] hash(byte[] data) {
        SM3Digest sm3Digest = new SM3Digest();
        sm3Digest.update(data, 0, data.length);
        byte[] result = new byte[sm3Digest.getDigestSize()];
        sm3Digest.doFinal(result, 0);
        return result;
    }

    public static byte[] hmac(byte[] key, byte[] data) {
        KeyParameter keyParameter = new KeyParameter(key);
        SM3Digest sm3Digest = new SM3Digest();
        HMac hmac = new HMac(sm3Digest);
        hmac.init(keyParameter);
        hmac.update(data, 0, data.length);
        byte[] result = new byte[sm3Digest.getDigestSize()];
        hmac.doFinal(result, 0);
        return result;
    }

}
