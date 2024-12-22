package vn.thuanflu.identityservices.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vn.thuanflu.identityservices.dto.response.ApiResponse;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse<String>> handleRuntimeException(RuntimeException exception) {
        log.error("Exception: ", exception);

        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
                .message(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage())
                .code(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode())
                .build();

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<String>> handleAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();

        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
                .message(errorCode.getMessage())
                .code(errorCode.getCode()).build();

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse<String>> handleAccessDeniedException(AccessDeniedException exception) {
        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
                .message(ErrorCode.UNAUTHORIZED.getMessage())
                .code(ErrorCode.UNAUTHORIZED.getCode())
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ApiResponse<String> apiResponse = new ApiResponse<>();

        String enumKey = exception.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        try {
            errorCode = ErrorCode.valueOf(enumKey);
        } catch (IllegalArgumentException e){

        }

        apiResponse.setMessage(errorCode.getMessage());
        apiResponse.setCode(errorCode.getCode());

        return ResponseEntity.badRequest().body(apiResponse);
    }
}
