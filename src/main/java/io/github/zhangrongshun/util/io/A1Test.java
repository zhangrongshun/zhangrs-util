package io.github.zhangrongshun.util.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class A1Test {

    public static void main(String[] args) throws IOException {
//        Files.createDirectories(Paths.get("D:\\010\\020"));
//        Files.copy(Paths.get("D:\\1.json"), Paths.get("D:\\010\\2.json"), StandardCopyOption.REPLACE_EXISTING);
//        Files.copy(Paths.get("D:\\1.json"), Paths.get("D:\\3.json"), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
//        Files.move(Paths.get("D:\\1.json"), Paths.get("D:\\4.json"));
        try (Stream<Path> list = Files.list(Paths.get("D:\\IdeaProjects"))) {
            list.forEach(p -> {
                if (Files.exists(p) && Files.isDirectory(p)) {
                    System.out.println(1);
                }
            });
        }
    }

}
