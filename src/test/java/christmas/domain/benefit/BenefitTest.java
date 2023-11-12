package christmas.domain.benefit;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.Menu;
import christmas.domain.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BenefitTest {

    @DisplayName("증정 혜택의 혜택 받은 금액은 증정받은 메뉴의 가격과 개수의 곱이다.")
    @Test
    void getTotal() {
        // given
        final int count = 3;
        final Benefit benefit = new GiveawayBenefit(Menu.CHAMPAGNE, count, "샴페인 3개 증정");
        final Money expected = Menu.CHAMPAGNE.getMoney(count);

        // when
        final Money actual = benefit.getPromotedMoney();

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
