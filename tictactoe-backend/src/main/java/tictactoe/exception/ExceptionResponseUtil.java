package tictactoe.exception;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@UtilityClass
public class ExceptionResponseUtil {

    public ResponseEntity<ExceptionResponse> buildResponseEntity(HttpStatus status, String key, RuntimeException exception) {
        return ResponseEntity
                .status(status)
                .body(ExceptionResponse.builder()
                        .status(status.value())
                        .key(key)
                        .message(exception.getMessage())
                        .build()
                );
    }

}