package christmas.error;

public enum ExceptionCode {

    ILLEGAL_DOMAIN("올바르지 않은 도메인 상태입니다."),
    ILLEGAL_INPUT("올바르지 않은 입력입니다."),
    ILLEGAL_VISITED_DAY("유효하지 않은 날짜입니다. 다시 입력해 주세요."),
    ILLEGAL_MENU("유효하지 않은 주문입니다. 다시 입력해 주세요."),
    NEGATIVE_MONEY_AMOUNT("금액은 음수일 수 없습니다."),
    ILLEGAL_PROMOTION_CONDITION("올바르지 않은 이벤트 조건입니다."),
    ILLEGAL_BENEFIT_STRATEGY("올바르지 않은 이벤트 혜택 전략입니다.");
    private final String message;

    ExceptionCode(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
