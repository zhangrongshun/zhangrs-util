package io.github.zhangrongshun.util.sm;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Provider;
import java.security.Security;

public class SmBase {

    private static final Logger logger = LoggerFactory.getLogger(SmBase.class);

    static {
        final String name = "BC";
        Provider bc = Security.getProvider(name);
        if (bc == null) {
            Security.addProvider(new BouncyCastleProvider());
            bc = Security.getProvider(name);
            if (bc == null) {
                throw new SecurityException("BouncyCastle provider not available");
            }
        }
        if (logger.isInfoEnabled()) {
            logger.info("BouncyCastle provider available: {}", bc);
        }
    }

}
