package io.github.zhangrongshun.util.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FastByteArrayOutputStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.TreeSet;

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

    public static void main(String[] args) {
//        Path path = Paths.get("D:\\2.json");
//        Path path1 = Paths.get("D:\\2.json");
//        System.out.println(path.equals(path1));
//        System.out.println(path.toFile().equals(path1.toFile()));
//        Path path2 = Paths.get("D:\\2.json");
//        Path path3 = Paths.get("D:\\");
////        System.out.println(path3.compareTo(path2));
////        System.out.println(path2.compareTo(path3));
//        System.out.println(path3.relativize(path2));
//        System.out.println(path2.relativize(path3));
//        TreeSet<Path> treeSet = new TreeSet<>();
//        treeSet.add(path1);
//        treeSet.add(path2);
//        treeSet.add(path3);
//        System.out.println(treeSet);
        try {
            Files.walkFileTree(Paths.get("C:\\Users\\zhangrs\\.m2"), new FileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
