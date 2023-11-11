package christmas.error;

public class IllegalVisitedDayException extends CustomException{

    public IllegalVisitedDayException() {
        super(ExceptionCode.ILLEGAL_VISITED_DAY);
    }
}
