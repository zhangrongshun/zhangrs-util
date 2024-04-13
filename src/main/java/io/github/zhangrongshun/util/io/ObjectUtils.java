package io.github.zhangrongshun.util.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FastByteArrayOutputStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ObjectUtils {

    private static final Logger logger = LoggerFactory.getLogger(ObjectUtils.class);

    /**
     * 对象深拷贝
     */
    public static <T extends Serializable> T clone(T obj) throws IOException {
        if (obj == null) {
            return null;
        }
        try (FastByteArrayOutputStream fastByteArrayOutputStream = new FastByteArrayOutputStream(); ObjectOutputStream objectOutputStream = new ObjectOutputStream(fastByteArrayOutputStream)) {
            objectOutputStream.writeObject(obj);
            try (ObjectInputStream objectInputStream = new ObjectInputStream(fastByteArrayOutputStream.getInputStream())) {
                return uncheckedCast(objectInputStream.readObject());
            }
        } catch (ClassNotFoundException e) {
            throw new IOException(e);
        }
    }

//    private static void error(IOException e) {
//        if (logger.isErrorEnabled()) {
//            logger.error(e.getMessage(), e);
//        }
//    }

    /**
     * 强制类型转换
     */
    public static <T> T uncheckedCast(Object obj) {
        //noinspection unchecked
        return (T) obj;
    }

}
