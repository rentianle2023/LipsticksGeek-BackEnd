package fun.tianlefirstweb.www.exception;

public class EntityAlreadyExistException extends RuntimeException{

    public EntityAlreadyExistException() {
        super();
    }

    public EntityAlreadyExistException(String message) {
        super(message);
    }
}
