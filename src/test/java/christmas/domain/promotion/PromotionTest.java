package christmas.domain.promotion;

import static christmas.domain.Menu.CHAMPAGNE;
import static christmas.domain.Menu.STEAK;
import static christmas.domain.MenuCategory.MAIN;
import static christmas.support.OrderFixtures.스테이크_3개_샴페인_1병;
import static christmas.support.OrderFixtures.타파스_1개_샐러드_2개;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import christmas.domain.MenuCategory;
import christmas.domain.Money;
import christmas.domain.Order;
import christmas.domain.benefit.Benefit;
import christmas.error.IllegalDomainException;
import christmas.support.AlwaysCondition;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PromotionTest {

    @DisplayName("이벤트 조건이 없으면 예외를 던진다")
    @Test
    void shouldHaveCondition() {
        // given & when & then
        assertThatThrownBy(() -> new FixedDiscountPromotion(null, "혜택", new Money(1000)))
                .isInstanceOf(IllegalDomainException.class);
    }

    @DisplayName("이벤트는 조건이 참일 때 받을 수 있다.")
    @Test
    void canTakePromotion() {
        // given
        final Promotion promotion = new FixedDiscountPromotion(
                new AlwaysCondition(true), "혜택", new Money(1000));
        final Order order = 타파스_1개_샐러드_2개.생성();

        // when
        final Optional<Benefit> actual = promotion.take(order);

        // then
        assertThat(actual).isPresent();
    }

    @DisplayName("이벤트는 조건이 거짓이면 받을 수 없다.")
    @Test
    void cantTakePromotion() {
        // given
        final Promotion promotion = new FixedDiscountPromotion(
                new AlwaysCondition(false), "혜택", new Money(1000));
        final Order order = 타파스_1개_샐러드_2개.생성();

        // when
        final Optional<Benefit> actual = promotion.take(order);

        // then
        assertThat(actual).isEmpty();
    }

    @DisplayName("고정 할인 혜택을 받는다.")
    @Test
    void takeFixedDiscountPromotion() {
        // given
        final Money expected = new Money(1000);
        final Promotion promotion = new FixedDiscountPromotion(
                new AlwaysCondition(true), "혜택", expected);
        final Order order = 타파스_1개_샐러드_2개.생성();

        // when
        final Optional<Benefit> actual = promotion.take(order);

        // then
        assertAll(() -> {
            assertThat(actual).isPresent();
            final Benefit benefit = actual.get();
            assertThat(benefit.getPromotedMoney()).isEqualTo(expected);
        });
    }

    @DisplayName("증정 혜택을 받는다.")
    @Test
    void takeGiveawayPromotion() {
        // given
        final Money expected = CHAMPAGNE.getMoney(2);
        final Promotion promotion = new GiveawayPromotion(
                new AlwaysCondition(true), "혜택", CHAMPAGNE, 2);
        final Order order = 타파스_1개_샐러드_2개.생성();

        // when
        final Optional<Benefit> actual = promotion.take(order);

        // then
        assertAll(() -> {
            assertThat(actual).isPresent();
            final Benefit benefit = actual.get();
            assertThat(benefit.getPromotedMoney()).isEqualTo(expected);
        });
    }

    @DisplayName("특정 메뉴 종류에 대한 할인을 받는다.")
    @Test
    void takeMenuDiscountPromotion() {
        // given
        final Money promoted = new Money(2000);
        final Promotion promotion = new MenuDiscountPromotion(
                new AlwaysCondition(true), "혜택", MAIN, promoted);
        final Order order = 스테이크_3개_샴페인_1병.생성();
        final Money expected = promoted.multiply(3);

        // when
        final Optional<Benefit> actual = promotion.take(order);

        // then
        assertAll(() -> {
            assertThat(actual).isPresent();
            final Benefit benefit = actual.get();
            assertThat(benefit.getPromotedMoney()).isEqualTo(expected);
        });
    }

    @DisplayName("날짜에 따라 증가하는 할인을 받는다.")
    @Test
    void takeTemporalPromotion() {
        // given
        final Money base = new Money(1000);
        final Money increase = new Money(100);
        final LocalDate start = LocalDate.of(2023, 12, 1);
        final Promotion promotion = new TemporalDiscountPromotion(
                new AlwaysCondition(true), "혜택", base, increase, start);
        final Order order = 스테이크_3개_샴페인_1병.생성();

        final int daysAfter = Long.valueOf(ChronoUnit.DAYS.between(start, 스테이크_3개_샴페인_1병.visitedAt))
                .intValue();
        final Money expected = base.add(increase.multiply(daysAfter));

        // when
        final Optional<Benefit> actual = promotion.take(order);

        // then
        assertAll(() -> {
            assertThat(actual).isPresent();
            final Benefit benefit = actual.get();
            assertThat(benefit.getPromotedMoney()).isEqualTo(expected);
        });
    }

}
