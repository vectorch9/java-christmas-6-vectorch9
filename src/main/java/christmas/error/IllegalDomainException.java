package christmas.error;

import static christmas.error.ExceptionCode.ILLEGAL_DOMAIN;

public class IllegalDomainException extends CustomException{

    public IllegalDomainException() {
        super(ILLEGAL_DOMAIN);
    }

    public IllegalDomainException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
