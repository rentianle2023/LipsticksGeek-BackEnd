package fun.tianlefirstweb.www.exception;

public class EntityNotExistException extends RuntimeException {
    public EntityNotExistException() {
        super();
    }

    public EntityNotExistException(String message) {
        super(message);
    }
}
