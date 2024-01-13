package io.github.zhangrongshun.util.io;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FileUtils {

    public static CustomizedLineIterator customizedLineIterator(final File file, final Charset charset, String lineSeparator) throws IOException {
        if (lineSeparator == null || lineSeparator.isEmpty()) {
            throw new IOException("lineSeparator is Empty");
        }
        InputStream inputStream = null;
        try {
            inputStream = Files.newInputStream(file.toPath());
            return new CustomizedLineIterator(new InputStreamReader(inputStream, charset), lineSeparator);
        } catch (final IOException ex) {
            IOUtils.closeQuietly(inputStream, ex::addSuppressed);
            throw ex;
        }
    }

    public static void main(String[] args) throws IOException {
        try (CustomizedLineIterator customizedLineIterator = FileUtils.customizedLineIterator(new File("D:\\log\\1.dat"), StandardCharsets.UTF_8, "\n")) {
            int i = 1;
            while (customizedLineIterator.hasNext()) {
                String next = customizedLineIterator.next();
                System.out.println("第" + i + "行, " + next);
                i++;
            }
            System.out.println(i);
        }
        try (LineIterator customizedLineIterator = org.apache.commons.io.FileUtils.lineIterator(new File("D:\\log\\1.dat"), StandardCharsets.UTF_8.name())) {
            int i = 1;
            while (customizedLineIterator.hasNext()) {
                String next = customizedLineIterator.next();
                System.out.println("第" + i + "行, " + next);
                i++;
            }
            System.out.println(i);
        }
    }

}
