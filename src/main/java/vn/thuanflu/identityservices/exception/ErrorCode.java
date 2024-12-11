package vn.thuanflu.identityservices.exception;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception"),

    USER_EXISTED(1001, "User already exists"),
    USER_NOT_FOUND(1002, "User not found"),

    ;
    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
