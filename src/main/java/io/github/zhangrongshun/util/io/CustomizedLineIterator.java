package io.github.zhangrongshun.util.io;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

public class CustomizedLineIterator implements Iterator<String>, Closeable {

    private final String lineSeparator;

    private final int lineSeparatorCharsCount;

    private final BufferedReader bufferedReader;

    private String cachedLine;

    private boolean finished;


    public CustomizedLineIterator(Reader reader, String lineSeparator) {
        Objects.requireNonNull(reader, "reader");
        if (lineSeparator == null || lineSeparator.isEmpty()) {
            throw new IllegalArgumentException("lineSeparator");
        }
        this.lineSeparator = lineSeparator;
        this.lineSeparatorCharsCount = (int) lineSeparator.chars().count();
        if (reader instanceof BufferedReader) {
            bufferedReader = (BufferedReader) reader;
        } else {
            bufferedReader = new BufferedReader(reader);
        }
    }

    @Override
    public boolean hasNext() {
        if (cachedLine != null) {
            return true;
        }
        if (finished) {
            return false;
        }
        char[] lineSeparatorChars = new char[this.lineSeparatorCharsCount];
        List<Character> cs = new ArrayList<>();
        int b;
        try {
            while ((b = bufferedReader.read()) != -1) {
                cs.add((char) b);
                char[] temp = new char[this.lineSeparatorCharsCount];
                System.arraycopy(lineSeparatorChars, 1, temp, 0, this.lineSeparatorCharsCount - 1);
                temp[lineSeparatorCharsCount - 1] = (char) b;
                String s = new String(temp);
                if (this.lineSeparator.equals(s)) {
                    cachedLine = getCacheLine(cs);
                    cs.clear();
                    return true;
                }
                lineSeparatorChars = temp;
            }
            finished = true;
            cachedLine = getCacheLine(cs);
            return cachedLine != null;
        } catch (IOException ioe) {
            IOUtils.closeQuietly(this, ioe::addSuppressed);
            throw new IllegalStateException(ioe);
        }
    }

    private String getCacheLine(List<Character> cs) {
        int charCount = finished ? cs.size() : cs.size() - this.lineSeparatorCharsCount;
        if (charCount == 0 && finished) {
            return null;
        }
        char[] cs1 = new char[charCount];
        for (int i = 0; i < charCount; i++) {
            cs1[i] = cs.get(i);
        }
        return new String(cs1);
    }

    @Override
    public String next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more lines");
        }
        final String currentLine = cachedLine;
        cachedLine = null;
        return currentLine;
    }

    @Override
    public void close() throws IOException {
        IOUtils.close(bufferedReader);
    }

}
