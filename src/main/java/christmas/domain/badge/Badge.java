package christmas.domain.badge;

import christmas.domain.Bill;

public enum Badge {

    SANTA("산타", 20000),
    TREE("트리", 10000),
    STAR("스타", 5000),
    NONE("없음", 0);

    private final String name;

    private final int threshold;

    Badge(final String name, final int threshold) {
        this.name = name;
        this.threshold = threshold;
    }

    public static Badge fromPromotedAmount(final Bill bill) {
        for (final Badge badge : values()) {
            if (bill.getPromoted().getAmount() >= badge.threshold) {
                return badge;
            }
        }
        return NONE;
    }

    public String getName() {
        return name;
    }
}
