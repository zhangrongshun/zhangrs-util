package io.github.zhangrongshun.util.io;

import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FTest {

    public static void main(String[] args) {
        try (CustomizedLineIterator customizedLineIterator = FileUtils.customizedLineIterator(new File("D:\\log\\1.dat"), StandardCharsets.UTF_8, "\r\n")) {
            int i = 0;
            while (customizedLineIterator.hasNext()) {
                i++;
                System.out.println("第" + i + "行，" + customizedLineIterator.next());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (LineIterator lineIterator = org.apache.commons.io.FileUtils.lineIterator(new File("D:\\log\\1.dat"), StandardCharsets.UTF_8.name())) {
            int i = 0;
            while (lineIterator.hasNext()) {
                i++;
                System.out.println("第" + i + "行，" + lineIterator.next());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
