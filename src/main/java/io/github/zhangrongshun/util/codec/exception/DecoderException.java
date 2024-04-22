package io.github.zhangrongshun.util.codec.exception;

public class DecoderException extends Exception {

    public DecoderException(String message) {
        super(message);
    }

    public DecoderException(String message, NumberFormatException e) {
        super(message, e);
    }
}
