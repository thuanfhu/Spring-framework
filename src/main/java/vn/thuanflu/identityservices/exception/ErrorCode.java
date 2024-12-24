package vn.thuanflu.identityservices.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    // General errors
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Invalid key", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1002, "Not authenticated!", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1003, "Access denied, you don't have permission to access this endpoint!", HttpStatus.FORBIDDEN),

    // User errors
    USER_EXISTED(1051, "User already exists", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1052, "User not found", HttpStatus.NOT_FOUND),
    PASSWORD_MIN(1053, "Password must be 8 characters", HttpStatus.BAD_REQUEST),
    FIRSTNAME_MIN(1054, "First name must be 3 characters", HttpStatus.BAD_REQUEST),

    // Permission errors
    PERMISSION_NOT_FOUND(1101, "Permission not found", HttpStatus.NOT_FOUND),

    // Role errors
    ROLE_NOT_FOUND(1151, "Role not found", HttpStatus.NOT_FOUND),

    ;

    int code;
    String message;
    HttpStatusCode statusCode;
}
