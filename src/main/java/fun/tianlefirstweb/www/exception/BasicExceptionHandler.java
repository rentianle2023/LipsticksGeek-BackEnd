package fun.tianlefirstweb.www.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class BasicExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ExceptionBody exceptionBody = new ExceptionBody(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now()
        );
        return ResponseEntity.badRequest().body(exceptionBody);
    }
}
