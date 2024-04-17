package io.github.zhangrongshun.util.sm;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

public class SmBase {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

}
