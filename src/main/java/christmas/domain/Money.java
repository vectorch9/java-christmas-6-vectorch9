package christmas.domain;

import static christmas.error.ExceptionCode.NEGATIVE_MONEY_AMOUNT;

import christmas.error.IllegalDomainException;
import java.util.Objects;

public class Money {

    private static final int ZERO_AMOUNT = 0;

    public static final Money ZERO = new Money(ZERO_AMOUNT);

    private final int amount;

    public Money(final int amount) {
        validateNotNegative(amount);
        this.amount = amount;
    }

    private void validateNotNegative(final int amount) {
        if (amount < ZERO_AMOUNT) {
            throw new IllegalDomainException(NEGATIVE_MONEY_AMOUNT);
        }
    }

    public int getAmount() {
        return amount;
    }

    public Money add(final Money money) {
        return new Money(amount + money.amount);
    }

    public Money subtract(final Money money) {
        return new Money(amount - money.amount);
    }

    public Money multiply(final int multiplier) {
        return new Money(amount * multiplier);
    }

    public boolean isGreaterThan(final Money money) {
        return amount > money.amount;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Money money = (Money) o;
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

}
