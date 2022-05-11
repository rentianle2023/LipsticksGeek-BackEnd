package fun.tianlefirstweb.www.exception;

public class UnableToConnectException extends RuntimeException{

    public UnableToConnectException() {
        super();
    }

    public UnableToConnectException(String message) {
        super(message);
    }
}
