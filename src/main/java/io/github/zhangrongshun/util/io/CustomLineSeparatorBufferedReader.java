package io.github.zhangrongshun.util.io;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;

public class CustomLineSeparatorBufferedReader extends BufferedReader {

    private final String lineSeparator;

    private final LinkedBlockingQueue<String> cacheLines;

    private final int defaultCharBufferSize = 8192;

    private char[] cacheChars;

    public CustomLineSeparatorBufferedReader(Reader in, String lineSeparator) {
        super(in);
        this.lineSeparator = Objects.requireNonNull(lineSeparator);
        cacheLines = new LinkedBlockingQueue<>(defaultCharBufferSize);
        cacheChars = new char[0];
    }

    @Override
    public String readLine() throws IOException {
        if (cacheLines.peek() != null) {
            return cacheLines.poll();
        }
        try {
            scan:
            {
                for (; ; ) {
                    char[] cs = new char[defaultCharBufferSize];
                    int read = read(cs, 0, defaultCharBufferSize);
                    int ll = cacheChars.length;
                    if (read == IOUtils.EOF) {
                        if (ll > 0) {
                            cacheLines.add(new String(cacheChars));
                        }
                        break scan;
                    }
                    char[] temp = new char[ll + read];
                    if (ll > 0) {
                        System.arraycopy(cacheChars, 0, temp, 0, ll);
                    }
                    if (read > 0) {
                        System.arraycopy(cs, 0, temp, ll, read);
                    }
                    String s = new String(temp);
                    String[] split = StringUtils.splitByWholeSeparatorPreserveAllTokens(s, lineSeparator);
                    if (split != null && split.length > 0) {
                        if (split.length > 1) {
                            int idx = 0;
                            for (String line : split) {
                                if (idx < split.length - 1) {
                                    cacheLines.add(line);
                                }
                                idx++;
                            }
                            String last = split[split.length - 1];
                            cacheChars = last.toCharArray();
                            break scan;
                        }
                    }
                    cacheChars = temp;
                }
            }
            if (cacheLines.peek() != null) {
                return cacheLines.poll();
            }
            return null;
        } catch (IOException ioe) {
            IOUtils.closeQuietly(this, ioe::addSuppressed);
            throw ioe;
        }
    }

}
