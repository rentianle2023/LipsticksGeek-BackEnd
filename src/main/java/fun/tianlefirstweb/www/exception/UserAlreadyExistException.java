package fun.tianlefirstweb.www.exception;

public class UserAlreadyExistException extends RuntimeException{

    public UserAlreadyExistException() {
        super();
    }

    public UserAlreadyExistException(String message) {
        super(message);
    }
}
