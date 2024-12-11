package vn.thuanflu.identityservices.exception;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception"),
    INVALID_KEY(1001, "Invalid key"),

    USER_EXISTED(1002, "User already exists"),
    USER_NOT_FOUND(1003, "User not found"),

    PASSWORD_MIN(1004, "Password must be 8 characters"),
    FIRSTNAME_MIN(1005, "First name must be 3 characters"),

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
