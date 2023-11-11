package christmas.error;

public class IllegalMenuException extends CustomException{

    public IllegalMenuException() {
        super(ExceptionCode.ILLEGAL_MENU);
    }
}
