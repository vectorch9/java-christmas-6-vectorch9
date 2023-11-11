package christmas;

import christmas.controller.Controller;
import christmas.domain.Shop;
import christmas.domain.promotion.Promotion;
import christmas.presentation.ErrorView;
import christmas.presentation.InputView;
import christmas.presentation.OutputView;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        final Controller controller = configureController();
        controller.run();
    }

    private static Controller configureController() {
        final InputView inputView = new InputView();
        final OutputView outputView = new OutputView();
        final ErrorView errorView = new ErrorView();
        final List<Promotion> promotions = PromotionConfig.getPromotions();
        final Shop shop = new Shop(promotions);
        return new Controller(shop, inputView, outputView, errorView);
    }
}
