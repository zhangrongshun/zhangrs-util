package io.github.zhangrongshun.util.io;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Set;
import java.util.TreeSet;

public class TarUtils {

    private static final Logger log = LoggerFactory.getLogger(TarUtils.class);

    public static void tar(Path workPath, Path dest, Set<Path> paths) {
        Assert.notNull(workPath, "workPath");
        Assert.notNull(dest, "dest");
        Assert.notEmpty(paths, "paths");
        TreeSet<Path> sortedPaths = new TreeSet<>(paths);
        try (TarArchiveOutputStream tarArchiveOutputStream = new TarArchiveOutputStream(new BufferedOutputStream(Files.newOutputStream(dest)))) {
            for (Path p : sortedPaths) {
                if (Files.isRegularFile(p)) {
                    putArchiveEntry(workPath, p, tarArchiveOutputStream);
                } else if (Files.isDirectory(p)) {
                    Files.walkFileTree(p, new SimpleFileVisitor<Path>() {
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

    public static void main(String[] args) {
        TreeSet<Path> objects = new TreeSet<>();
        objects.add(Paths.get("D:\\1\\新建文件夹"));
        objects.add(Paths.get("D:\\1\\新建 文本文档 (2).txt"));
        tar(Paths.get("D:\\1"), Paths.get("D:\\1.tar"), objects);
    }

}
