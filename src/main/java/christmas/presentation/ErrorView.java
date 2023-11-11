package christmas.presentation;

import christmas.error.CustomException;

public class ErrorView {

    private static final String ERROR_PREFIX = "[ERROR] ";

    private static final String RUNTIME_EXCEPTION_MESSAGE = "알 수 없는 예외가 발생했습니다.";

    public ErrorView() {
    }

    public void printException(final RuntimeException exception) {
        System.out.print(ERROR_PREFIX);
        if (exception instanceof CustomException) {
            System.out.println(exception.getMessage());
            return;
        }
        System.out.println(RUNTIME_EXCEPTION_MESSAGE);
    }

}
