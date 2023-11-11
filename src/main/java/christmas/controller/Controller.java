package christmas.controller;

import static christmas.error.ExceptionCode.EXCEED_MAX_TRY;

import christmas.domain.Bill;
import christmas.domain.Order;
import christmas.domain.Shop;
import christmas.domain.badge.Badge;
import christmas.error.CustomException;
import christmas.presentation.ErrorView;
import christmas.presentation.InputView;
import christmas.presentation.OutputView;
import java.time.LocalDate;
import java.util.function.Function;
import java.util.function.Supplier;

public class Controller {

    private static final int MAX_TRY = 5;

    private final Shop shop;

    private final InputView inputView;

    private final OutputView outputView;

    private final ErrorView errorView;

    public Controller(final Shop shop, final InputView inputView, final OutputView outputView,
            final ErrorView errorView) {
        this.shop = shop;
        this.inputView = inputView;
        this.outputView = outputView;
        this.errorView = errorView;
    }

    public void run() {
        try {
            outputView.printWelcome();
            final LocalDate visitedAt = visit();
            final Order order = order(visitedAt);
            outputView.printWelcomeFooter();
            outputView.printOrder(order);
            final Bill bill = shop.takeBill(order);
            outputView.printBill(bill);
            final Badge badge = Badge.fromPromotedAmount(bill);
            outputView.printBadge(badge);
        } catch (final RuntimeException e) {
            errorView.printException(e);
        }
    }

    private LocalDate visit() {
        return readAndValidate(inputView::readVisitedDay, Function.identity());
    }

    private Order order(final LocalDate visitedAt) {
        return readAndValidate(inputView::readMenus, (menus) -> shop.order(visitedAt, menus));
    }

    private <S, T> T readAndValidate(Supplier<S> inputSupplier, Function<S, T> converter) {
        for (int time = 1; time <= MAX_TRY; ++time) {
            try {
                final S input = inputSupplier.get();
                return converter.apply(input);
            } catch (final RuntimeException e) {
                errorView.printException(e);
            }
        }
        throw new CustomException(EXCEED_MAX_TRY);
    }
}
