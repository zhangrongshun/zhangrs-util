package io.github.zhangrongshun.util.codec.exception;

public class BaseNDecoderException extends Exception {

    public BaseNDecoderException(String message) {
        super(message);
    }

    public BaseNDecoderException(String message, NumberFormatException e) {
        super(message, e);
    }
}
