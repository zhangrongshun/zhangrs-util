package io.github.zhangrongshun.util.io;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Collections;

public class TarUtils {

    private static final Logger log = LoggerFactory.getLogger(TarUtils.class);

    public static void tar(Path workPath, Path dest, String... paths) {
        Assert.notNull(workPath, "workPath");
        Assert.notNull(dest, "dest");
        Assert.notEmpty(paths, "paths");
        try (TarArchiveOutputStream tarArchiveOutputStream = new TarArchiveOutputStream(new BufferedOutputStream(Files.newOutputStream(dest)))) {
            tarArchiveOutputStream.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);
            for (String p : paths) {
                Path path = Paths.get(workPath.toString(), p).normalize();
                if (Files.isRegularFile(path)) {
                    putArchiveEntry(workPath, path, tarArchiveOutputStream);
                } else if (Files.isDirectory(path)) {
                    Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                        @Override
                        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                            putArchiveEntry(workPath, dir, tarArchiveOutputStream);
                            return super.preVisitDirectory(dir, attrs);
                        }

                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                            putArchiveEntry(workPath, file, tarArchiveOutputStream);
                            return super.visitFile(file, attrs);
                        }
                    });
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void putArchiveEntry(Path workPath, Path p, TarArchiveOutputStream tarArchiveOutputStream) throws IOException {
        TarArchiveEntry entry = new TarArchiveEntry(p.toFile(), workPath.relativize(p).normalize().toString());
        tarArchiveOutputStream.putArchiveEntry(entry);
        if (Files.isRegularFile(p)) {
            Files.copy(p, tarArchiveOutputStream);
        }
        tarArchiveOutputStream.closeArchiveEntry();
    }

    public static void main(String[] args) throws IOException {
//        TreeSet<Path> objects = new TreeSet<>();
//        objects.add(Paths.get( "repository"));
//        objects.add(Paths.get(".gradle-enterprise"));
//        objects.add(Paths.get("1.tar"));
//        tar(Paths.get("C:\\Users\\zhangrs\\.m2"), Paths.get("D:\\1.tar"), objects);
//        Files.copy(Paths.get("D:\\1\\"), Paths.get("D:\\2\\"), StandardCopyOption.REPLACE_EXISTING);
//        Files.walkFileTree(Paths.get("D:\\1\\"), new SimpleFileVisitor<Path>(){
//
//        });
//        tar(Paths.get("D:\\010\\"), Paths.get("D:\\010\\111\\3.tar"), "2.json", "020/1.txt", "020", "020");
//        Path path = Paths.get("/d/1", "../010").normalize();
//        System.out.println(path);
        byte[] bytes = "1287z".getBytes(StandardCharsets.UTF_8);
        System.out.println(Collections.singletonList(bytes));
        String s = Hex.encodeHexString(bytes);
        System.out.println(s);
    }

}
