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

    private List<Character> cachedLineChars;


    public CustomizedLineIterator(Reader reader, String lineSeparator) {
        Objects.requireNonNull(reader, "reader");
        if (lineSeparator == null || lineSeparator.isEmpty()) {
            throw new IllegalArgumentException("lineSeparator");
        }
        this.lineSeparator = lineSeparator;
        this.lineSeparatorCharsCount = lineSeparator.length();
        if (reader instanceof BufferedReader) {
            bufferedReader = (BufferedReader) reader;
        } else {
            bufferedReader = new BufferedReader(reader);
        }
        cachedLineChars = new ArrayList<>();
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
        try {
            for (; ; ) {
                int b = bufferedReader.read();
                if (b == IOUtils.EOF) {
                    finished = true;
                    return getNextLine();
                } else {
                    char c = (char) b;
                    cachedLineChars.add(c);
                    char[] temp = new char[this.lineSeparatorCharsCount];
                    System.arraycopy(lineSeparatorChars, 1, temp, 0, this.lineSeparatorCharsCount - 1);
                    temp[lineSeparatorCharsCount - 1] = c;
                    lineSeparatorChars = temp;
                    String s = new String(temp);
                    if (this.lineSeparator.equals(s)) {
                        return getNextLine();
                    }
                }
            }
        } catch (IOException ioe) {
            IOUtils.closeQuietly(this, ioe::addSuppressed);
            throw new IllegalStateException(ioe);
        }
    }

    private boolean getNextLine() {
        int charCount = finished ? cachedLineChars.size() : cachedLineChars.size() - this.lineSeparatorCharsCount;
        if (charCount > 0 || !finished) {
            char[] cs1 = new char[charCount];
            for (int i = 0; i < charCount; i++) {
                cs1[i] = cachedLineChars.get(i);
            }
            cachedLine = new String(cs1);
        }
        cachedLineChars.clear();
        return cachedLine != null;
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
        cachedLineChars = null;
        IOUtils.close(bufferedReader);
    }

}
