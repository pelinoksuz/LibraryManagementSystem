package edu.ozu.cs202project.exception;

public class LMSException extends RuntimeException{

    private final String code;

    public LMSException(String code, String message) {
        super(message);
        this.code = code;
    }

    public LMSException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
