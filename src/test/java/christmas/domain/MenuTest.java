package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.error.IllegalMenuException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MenuTest {

    @DisplayName("메뉴 이름을 통해 메뉴를 생성한다.")
    @Test
    void getSalad() {
        // given & when
        final Menu menu = Menu.fromName("시저샐러드");

        // then
        assertThat(menu).isEqualTo(Menu.SALAD);
    }

    @DisplayName("없는 이름을 넣으면 예외를 던진다.")
    @Test
    void getIllegalMenu() {
        // given & when & then
        assertThatThrownBy(() -> Menu.fromName("시저")).isInstanceOf(IllegalMenuException.class);
    }

    @DisplayName("메뉴의 종류가 다르면 true를 반환한다.")
    @Test
    void compareCategoryTrue() {
        // given & when
        final boolean actual = Menu.SALAD.compareCategory(MenuCategory.APPETIZER);

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("메뉴의 종류가 다르면 false를 반환한다.")
    @Test
    void compareCategoryFalse() {
        // given & when
        final boolean actual = Menu.SALAD.compareCategory(MenuCategory.DRINK);

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("메뉴를 여러개 시켰을 때의 가격을 얻는다.")
    @Test
    void getMoneyWithCount() {
        // given
        final int count = 3;
        final Money expected = new Money(8000 * count);

        // when
        final Money actual = Menu.SALAD.getMoney(count);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
