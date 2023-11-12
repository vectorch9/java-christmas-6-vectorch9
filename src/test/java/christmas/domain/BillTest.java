package christmas.domain;

import static christmas.support.DiscountBenefitFixtures.이천원_할인_혜택;
import static christmas.support.DiscountBenefitFixtures.천원_할인_혜택;
import static christmas.support.GiveawayBenefitFixtures.샴페인_증정_혜택;
import static christmas.support.GiveawayBenefitFixtures.콜라_증정_혜택;
import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.benefit.Benefit;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BillTest {

    @DisplayName("총액을 확인한다.")
    @Test
    void getTotal() {
        // given
        final Money total = new Money(50000);
        final Bill bill = new Bill(total, Collections.emptyList());

        // when
        final Money actual = bill.getTotal();

        // then
        assertThat(actual).isEqualTo(total);
    }

    @DisplayName("혜택 받은 금액의 합을 확인한다.")
    @Test
    void getPromoted() {
        // given
        final List<Benefit> benefits = List.of(천원_할인_혜택.생성(), 천원_할인_혜택.생성(), 이천원_할인_혜택.생성());
        final Money total = new Money(50000);
        final Bill bill = new Bill(total, benefits);
        final Money expected = 천원_할인_혜택.할인_금액().add(천원_할인_혜택.할인_금액()).add(이천원_할인_혜택.할인_금액());

        // when
        final Money actual = bill.getPromoted();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("할인 받은 이후 결제할 금액의 합을 확인한다.")
    @Test
    void getPromotedTotal() {
        // given
        final List<Benefit> benefits = List.of(천원_할인_혜택.생성(), 천원_할인_혜택.생성(), 이천원_할인_혜택.생성(),
                콜라_증정_혜택.생성());
        final Money total = new Money(50000);
        final Bill bill = new Bill(total, benefits);
        final Money subtractor = 천원_할인_혜택.할인_금액().add(천원_할인_혜택.할인_금액()).add(이천원_할인_혜택.할인_금액());
        final Money expected = total.subtract(subtractor);

        // when
        final Money actual = bill.getPromotedTotal();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("혜택들을 확인한다.")
    @Test
    void getBenefits() {
        // given
        final List<Benefit> expected = List.of(천원_할인_혜택.생성(), 천원_할인_혜택.생성(), 이천원_할인_혜택.생성(),
                콜라_증정_혜택.생성());
        final Money total = new Money(50000);
        final Bill bill = new Bill(total, expected);

        // when
        final List<Benefit> actual = bill.getBenefits();

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @DisplayName("증정받은 메뉴를 확인한다.")
    @Test
    void getGiveawayMenus() {
        // given
        final List<Benefit> benefits = List.of(천원_할인_혜택.생성(), 샴페인_증정_혜택.생성(), 콜라_증정_혜택.생성());
        final Money total = new Money(50000);
        final Bill bill = new Bill(total, benefits);
        final Map<Menu, Integer> expected = Map.of(샴페인_증정_혜택.menu, 샴페인_증정_혜택.count,
                콜라_증정_혜택.menu, 콜라_증정_혜택.count);

        // when
        final Map<Menu, Integer> actual = bill.getGiveawayMenus();

        // then
        assertThat(actual).containsExactlyInAnyOrderEntriesOf(expected);
    }
}
