package christmas.domain.promotion;

import static christmas.support.OrderFixtures.스테이크_3개_샴페인_1병;
import static christmas.support.OrderFixtures.타파스_1개_샐러드_2개;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import christmas.domain.Money;
import christmas.domain.Order;
import christmas.domain.promotion.condition.DayOfWeekCondition;
import christmas.domain.promotion.condition.LocalDateCondition;
import christmas.domain.promotion.condition.MonthCondition;
import christmas.domain.promotion.condition.PeriodCondition;
import christmas.domain.promotion.condition.TotalMoneyCondition;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PromotionConditionTest {

    @DisplayName("요일을 확인한다.")
    @Test
    void checkDayOfWeekCondition() {
        // given
        final LocalDate friday = LocalDate.of(2023, 12, 1);
        final LocalDate saturday = LocalDate.of(2023, 12, 2);
        final Order fridayOrder = new Order(스테이크_3개_샴페인_1병.menus, friday);
        final Order saturdayOrder = new Order(스테이크_3개_샴페인_1병.menus, saturday);
        final PromotionCondition condition = new DayOfWeekCondition(List.of(DayOfWeek.FRIDAY));

        // when
        final boolean fridayActual = condition.canReceive(fridayOrder);
        final boolean saturdayActual = condition.canReceive(saturdayOrder);

        // then
        assertAll(() -> {
            assertThat(fridayActual).isTrue();
            assertThat(saturdayActual).isFalse();
        });
    }

    @DisplayName("날짜를 확인한다.")
    @Test
    void checkDateCondition() {
        // given
        final LocalDate sameDate = LocalDate.of(2023, 12, 1);
        final LocalDate anothetDate = LocalDate.of(2023, 12, 2);
        final Order sameDateOrder = new Order(스테이크_3개_샴페인_1병.menus, sameDate);
        final Order anothetDateOrder = new Order(스테이크_3개_샴페인_1병.menus, anothetDate);
        final PromotionCondition condition = new LocalDateCondition(sameDate);

        // when
        final boolean sameDateActual = condition.canReceive(sameDateOrder);
        final boolean anothetDateActual = condition.canReceive(anothetDateOrder);

        // then
        assertAll(() -> {
            assertThat(sameDateActual).isTrue();
            assertThat(anothetDateActual).isFalse();
        });
    }

    @DisplayName("월을 확인한다.")
    @Test
    void checkMonthCondition() {
        // given
        final LocalDate sameDate = LocalDate.of(2023, 12, 1);
        final LocalDate anothetDate = LocalDate.of(2023, 11, 2);
        final Order sameDateOrder = new Order(스테이크_3개_샴페인_1병.menus, sameDate);
        final Order anothetDateOrder = new Order(스테이크_3개_샴페인_1병.menus, anothetDate);
        final PromotionCondition condition = new MonthCondition(Month.DECEMBER);

        // when
        final boolean sameDateActual = condition.canReceive(sameDateOrder);
        final boolean anothetDateActual = condition.canReceive(anothetDateOrder);

        // then
        assertAll(() -> {
            assertThat(sameDateActual).isTrue();
            assertThat(anothetDateActual).isFalse();
        });
    }

    @DisplayName("기간을 확인한다.")
    @Test
    void checkPeriodCondition() {
        // given
        final LocalDate inDate = LocalDate.of(2023, 12, 1);
        final LocalDate outDate = LocalDate.of(2023, 12, 7);
        final Order inDateOrder = new Order(스테이크_3개_샴페인_1병.menus, inDate);
        final Order outDateOrder = new Order(스테이크_3개_샴페인_1병.menus, outDate);
        final PromotionCondition condition = new PeriodCondition(LocalDate.of(2023, 12, 1),
                LocalDate.of(2023, 12, 6));

        // when
        final boolean inDateActual = condition.canReceive(inDateOrder);
        final boolean outDateActual = condition.canReceive(outDateOrder);

        // then
        assertAll(() -> {
            assertThat(inDateActual).isTrue();
            assertThat(outDateActual).isFalse();
        });
    }

    @DisplayName("총액을 확인한다.")
    @Test
    void checkTotalCondition() {
        // given
        final LocalDate visitedAt = LocalDate.of(2023, 12, 1);
        final Order over30000 = new Order(스테이크_3개_샴페인_1병.menus, visitedAt);
        final Order under30000 = new Order(타파스_1개_샐러드_2개.menus, visitedAt);
        final PromotionCondition condition = new TotalMoneyCondition(new Money(30000));

        // when
        final boolean over30000Actual = condition.canReceive(over30000);
        final boolean under30000Actual = condition.canReceive(under30000);

        // then
        assertAll(() -> {
            assertThat(over30000Actual).isTrue();
            assertThat(under30000Actual).isFalse();
        });
    }
}
