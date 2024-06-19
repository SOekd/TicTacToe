package tictactoe.game;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tictactoe.exception.ExceptionResponse;
import tictactoe.exception.ExceptionResponseUtil;
import tictactoe.game.exception.GameNotFoundException;

@RestControllerAdvice
public class GameExceptionHandler {

    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleGameNotFoundException(GameNotFoundException exception) {
        return ExceptionResponseUtil.buildResponseEntity(HttpStatus.NOT_FOUND, "GAME_NOT_FOUND", exception);
    }

}
