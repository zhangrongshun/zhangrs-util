package io.github.zhangrongshun.util.exception;

public class DecoderException extends Exception {

    public DecoderException(String message) {
        super(message);
    }

    public DecoderException(String message, NumberFormatException e) {
        super(message, e);
    }
}
