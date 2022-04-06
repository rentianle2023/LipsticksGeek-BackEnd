package fun.tianlefirstweb.www.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
public class ExceptionBody {

    private final String message;
    private final HttpStatus status;
    private final ZonedDateTime zonedDateTime;

    public ExceptionBody(String message, HttpStatus status, ZonedDateTime zonedDateTime) {
        this.message = message;
        this.status = status;
        this.zonedDateTime = zonedDateTime;
    }
}
