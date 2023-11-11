package christmas;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.DayOfWeek.TUESDAY;
import static java.time.DayOfWeek.WEDNESDAY;

import christmas.domain.Menu;
import christmas.domain.MenuCategory;
import christmas.domain.Money;
import christmas.domain.promotion.FixedDiscountPromotion;
import christmas.domain.promotion.GiveawayPromotion;
import christmas.domain.promotion.MenuDiscountPromotion;
import christmas.domain.promotion.Promotion;
import christmas.domain.promotion.PromotionCondition;
import christmas.domain.promotion.TemporalDiscountPromotion;
import christmas.domain.promotion.condition.DayOfWeekCondition;
import christmas.domain.promotion.condition.LocalDateCondition;
import christmas.domain.promotion.condition.MonthCondition;
import christmas.domain.promotion.condition.PeriodCondition;
import christmas.domain.promotion.condition.TotalMoneyCondition;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public final class PromotionConfig {

    private PromotionConfig() {
    }

    public static List<Promotion> getPromotions() {
        final PromotionCondition totalMoneyCondition = new TotalMoneyCondition(new Money(10000));
        final PromotionCondition decemberCondition = new MonthCondition(Month.DECEMBER);
        final PromotionCondition defaultCondition = decemberCondition.and(totalMoneyCondition);

        final Promotion christmasDDay = new TemporalDiscountPromotion(
                new PeriodCondition(LocalDate.of(2023, 12, 1),
                        LocalDate.of(2023, 12, 25)
                ).and(defaultCondition),
                "크리스마스 디데이 할인",
                new Money(1000),
                new Money(100),
                LocalDate.of(2023, 12, 1)
        );
        final Promotion weekday = new MenuDiscountPromotion(
                new DayOfWeekCondition(List.of(SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY))
                        .and(defaultCondition),
                "평일 할인",
                MenuCategory.DESSERT,
                new Money(2023)
        );
        final Promotion weekend = new MenuDiscountPromotion(
                new DayOfWeekCondition(List.of(FRIDAY, SATURDAY)).and(defaultCondition),
                "주말 할인",
                MenuCategory.MAIN,
                new Money(2023)
        );

        final Promotion special = new FixedDiscountPromotion(
                (new DayOfWeekCondition(List.of(SUNDAY)).and(defaultCondition))
                        .or(new LocalDateCondition(LocalDate.of(2023, 12, 25))),
                "특별 할인",
                new Money(1000)
        );

        final Promotion champagne = new GiveawayPromotion(
                new TotalMoneyCondition(new Money(120000)).and(defaultCondition),
                "증정 이벤트",
                Menu.CHAMPAGNE, 1
        );

        return List.of(christmasDDay, weekday, weekend, special, champagne);
    }
}
