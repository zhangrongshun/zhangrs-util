package io.github.zhangrongshun.util.io;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ExtFiles {

    public static void main(String[] args) {
        Charset cs = StandardCharsets.UTF_8;
        Path path = Paths.get("");

    }

    public static Stream<String> lines(Path path, Charset cs, String lineSeparator) throws IOException {
        CustomLineSeparatorBufferedReader br = new CustomLineSeparatorBufferedReader(new InputStreamReader(Files.newInputStream(path), cs), lineSeparator);
        try {
            return br.lines().onClose(() -> IOUtils.closeQuietly(br));
        } catch (Error | RuntimeException e) {
            IOUtils.close(br, e::addSuppressed);
            throw e;
        }
    }

}
