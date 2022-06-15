package fun.tianlefirstweb.www.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class,EntityAlreadyExistException.class,EntityNotExistException.class})
    public ResponseEntity<ExceptionBody> handleIllegalArgumentException(RuntimeException e){
        ExceptionBody exceptionBody = new ExceptionBody(
                e.getMessage(),
                BAD_REQUEST,
                ZonedDateTime.now()
        );
        return ResponseEntity.badRequest().body(exceptionBody);
    }

    /**
     * server is acting as a gateway, but received an invalid response from upstream server
     */
    @ExceptionHandler(UnableToConnectException.class)
    public ResponseEntity<ExceptionBody> handleGatewayException(UnableToConnectException e){
        ExceptionBody exceptionBody = new ExceptionBody(
                e.getMessage(),
                BAD_GATEWAY,
                ZonedDateTime.now()
        );
        return ResponseEntity.status(BAD_GATEWAY).body(exceptionBody);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionBody> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        ExceptionBody exceptionBody = new ExceptionBody(
                e.getBindingResult().getFieldErrors().get(0).getDefaultMessage(),
                BAD_REQUEST,
                ZonedDateTime.now()
        );
        return ResponseEntity.badRequest().body(exceptionBody);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ExceptionBody> handleUserAlreadyExistException(UserAlreadyExistException e){
        ExceptionBody exceptionBody = new ExceptionBody(
                e.getMessage(),
                CONFLICT,
                ZonedDateTime.now()
        );
        return ResponseEntity.status(CONFLICT).body(exceptionBody);
    }
}
