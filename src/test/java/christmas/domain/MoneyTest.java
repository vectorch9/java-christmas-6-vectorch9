package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.error.IllegalDomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MoneyTest {

    @DisplayName("금액으로 음수를 입력하면 예외가 발생한다.")
    @Test
    void createMoneyWithNegativeNumber() {
        // given & when & then
        assertThatThrownBy(() -> new Money(-1))
                .isInstanceOf(IllegalDomainException.class);
    }

    @DisplayName("금액의 합을 계산한다.")
    @Test
    void add() {
        // given
        final int amount1 = 5000;
        final int amount2 = 15000;
        final Money money1 = new Money(amount1);
        final Money money2 = new Money(amount2);
        final Money expected = new Money(amount1 + amount2);

        // when
        final Money actual = money1.add(money2);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("금액의 차를 계산한다.")
    @Test
    void subtract() {
        // given
        final int amount1 = 15000;
        final int amount2 = 5000;
        final Money money1 = new Money(amount1);
        final Money money2 = new Money(amount2);
        final Money expected = new Money(amount1 - amount2);

        // when
        final Money actual = money1.subtract(money2);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("금액의 곱을 계산한다.")
    @Test
    void multiply() {
        // given
        final int amount = 5000;
        final int multiplier = 5;
        final Money money = new Money(amount);
        final Money expected = new Money(amount * multiplier);

        // when
        final Money actual = money.multiply(multiplier);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("금액을 비교한다.")
    @Test
    void isGreaterThan() {
        // given
        final Money money1 = new Money(5000);
        final Money money2 = new Money(15000);

        // when
        final boolean actual = money1.isGreaterThan(money2);

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("동일한 금액인지 확인한다.")
    @Test
    void equals() {
        // given
        final int amount = 5000;
        final Money money1 = new Money(amount);
        final Money money2 = new Money(amount);

        // when
        final boolean actual = money1.equals(money2);

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("동일한 금액이 아니라면 false를 반환한다.")
    @Test
    void notEquals() {
        // given
        final int amount = 5000;
        final int notSameAmount = 15000;
        final Money money1 = new Money(amount);
        final Money money2 = new Money(notSameAmount);

        // when
        final boolean actual = money1.equals(money2);

        // then
        assertThat(actual).isFalse();
    }
}
