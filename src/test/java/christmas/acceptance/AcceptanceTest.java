package christmas.acceptance;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static christmas.error.ExceptionCode.EXCEED_MAX_TRY;
import static christmas.error.ExceptionCode.ILLEGAL_MENU;
import static christmas.error.ExceptionCode.ILLEGAL_VISITED_DAY;
import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.test.NsTest;
import christmas.Application;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class AcceptanceTest extends NsTest {

    private static final String LINE_SEPARATOR = System.lineSeparator();

    private static final String ERROR_PREFIX = "[ERROR]";

    @DisplayName("정상 시나리오를 확인한다.")
    @Test
    void normalScenario() {
        assertSimpleTest(() -> {
            run("3", "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1");
            assertThat(output()).contains(
                    "<주문 메뉴>",
                    "<할인 전 총주문 금액>",
                    "<증정 메뉴>",
                    "<혜택 내역>",
                    "<총혜택 금액>",
                    "<할인 후 예상 결제 금액>",
                    "<12월 이벤트 배지>"
            );
        });
    }
    
    @DisplayName("혜택 내용이 없다면 없음을 출력한다.")
    @Test
    void noBenefit() {
        assertSimpleTest(() -> {
            run("26", "타파스-1,제로콜라-1");
            assertThat(output()).contains("<혜택 내역>" + LINE_SEPARATOR + "없음");
        });
    }

    @DisplayName("받은 증정 메뉴가 없다면 없음을 출력한다.")
    @Test
    void noGiveaway() {
        assertSimpleTest(() -> {
            run("26", "타파스-1,제로콜라-1");
            assertThat(output()).contains("<증정 메뉴>" + LINE_SEPARATOR + "없음");
        });
    }

    @DisplayName("받은 뱃지가 없다면 없음을 출력한다.")
    @Test
    void noBadge() {
        assertSimpleTest(() -> {
            run("26", "타파스-1,제로콜라-1");
            assertThat(output()).contains("<12월 이벤트 배지>" + LINE_SEPARATOR + "없음");
        });
    }

    @DisplayName("예외 발생 시 재입력을 받는다.")
    @Test
    void canTryAgain() {
        assertSimpleTest(() -> {
            runException("32", "31", "타파스-1,제로콜라-1");
            assertThat(output()).contains(ERROR_PREFIX);
        });
    }

    @DisplayName("올바르지 않은 날짜를 입력하면 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(strings = {"0", "-1", "32"})
    void illegalDate(final String date) {
        assertSimpleTest(() -> {
            runException(date);
            assertThat(output()).contains(ERROR_PREFIX, ILLEGAL_VISITED_DAY.getMessage());
        });
    }

    @DisplayName("메뉴의 포맷이 적절하지 않으면 예외를 던진다")
    @ParameterizedTest
    @ValueSource(strings = {"타파스-1제로콜라-1", "타퍼스1,제로콜라2", "타퍼스,제로콜라"})
    void illegalMenuFormat(final String menu) {
        assertSimpleTest(() -> {
            runException("31", menu);
            assertThat(output()).contains(ERROR_PREFIX, ILLEGAL_MENU.getMessage());
        });
    }
    
    @DisplayName("존재하지 않는 메뉴를 입력하면 예외를 던진다")
    @Test
    void notExistMenu() {
        assertSimpleTest(() -> {
            runException("31", "민트초코-1,하와이안피자-2");
            assertThat(output()).contains(ERROR_PREFIX, ILLEGAL_MENU.getMessage());
        });
    }

    @DisplayName("메뉴를 0개 주문하면 예외를 던진다.")
    @Test
    void noZeroCountMenu() {
        assertSimpleTest(() -> {
            runException("31", "타퍼스-0,제로콜라-2");
            assertThat(output()).contains(ERROR_PREFIX, ILLEGAL_MENU.getMessage());
        });
    }

    @DisplayName("메뉴가 중복되면 예외를 던진다.")
    @Test
    void noDuplicatedMenu() {
        assertSimpleTest(() -> {
            runException("31", "타퍼스-1,타퍼스-1,제로콜라-2");
            assertThat(output()).contains(ERROR_PREFIX, ILLEGAL_MENU.getMessage());
        });
    }

    @DisplayName("재입력은 예외가 발생한 부분부터 다시 받는다.")
    @Test
    void canTryAgainWinnerNumbers() {
        assertSimpleTest(() -> {
            runException("31", "샴페인-1,제로콜라-1", "타파스-1,제로콜라-1");
            assertThat(output()).contains(ERROR_PREFIX);
        });
    }

    @DisplayName("5번 이상 예외 발생 시 프로그램을 종료한다.")
    @Test
    void tryAgainOver5Times() {
        assertSimpleTest(() -> {
            runException("32", "32", "32", "32", "32", "32");
            assertThat(output()).contains(ERROR_PREFIX, EXCEED_MAX_TRY.getMessage());
        });
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
