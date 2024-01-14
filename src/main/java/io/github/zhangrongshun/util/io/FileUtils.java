package io.github.zhangrongshun.util.io;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
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
        } catch (IOException ex) {
            IOUtils.closeQuietly(inputStream, ex::addSuppressed);
            throw ex;
        }
    }

}
