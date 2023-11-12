package christmas.domain.badge;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.Bill;
import christmas.domain.Money;
import christmas.domain.benefit.DiscountBenefit;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BadgeTest {

    @DisplayName("영수증의 할인 금액이 20000원 이상이면 산타 뱃지를 얻는다.")
    @Test
    void getSantaBadge() {
        // given
        final Bill bill = new Bill(new Money(50000), List.of(new DiscountBenefit(new Money(20000), "할인1")));

        // when
        final Badge badge = Badge.fromPromotedAmount(bill);

        // then
        assertThat(badge).isEqualTo(Badge.SANTA);
    }

    @DisplayName("영수증의 할인 금액이 10000원 이상이면 트리 뱃지를 얻는다.")
    @Test
    void getTreeBadge() {
        // given
        final Bill bill = new Bill(new Money(50000), List.of(new DiscountBenefit(new Money(10000), "할인1")));

        // when
        final Badge badge = Badge.fromPromotedAmount(bill);

        // then
        assertThat(badge).isEqualTo(Badge.TREE);
    }

    @DisplayName("영수증의 할인 금액이 5000원 이상이면 스타 뱃지를 얻는다.")
    @Test
    void getStarBadge() {
        // given
        final Bill bill = new Bill(new Money(50000), List.of(new DiscountBenefit(new Money(5000), "할인1")));

        // when
        final Badge badge = Badge.fromPromotedAmount(bill);

        // then
        assertThat(badge).isEqualTo(Badge.STAR);
    }

    @DisplayName("영수증의 할인 금액이 5000원 미만이면 뱃지를 얻지 못한다.")
    @Test
    void getNoneBadge() {
        // given
        final Bill bill = new Bill(new Money(50000), List.of(new DiscountBenefit(new Money(4999), "할인1")));

        // when
        final Badge badge = Badge.fromPromotedAmount(bill);

        // then
        assertThat(badge).isEqualTo(Badge.NONE);
    }
}
