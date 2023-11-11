package christmas.error;

public class CustomException extends RuntimeException {

    public CustomException(final ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
    }
}