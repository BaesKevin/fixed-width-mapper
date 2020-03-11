package be.kevinbaes.fixed_width_mapper.testmodel;

import java.util.Objects;

public class PriceInfo {
    private int basePrice;
    private int discountPrice;

    public PriceInfo(int basePrice, int discountPrice) {
        this.basePrice = basePrice;
        this.discountPrice = discountPrice;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public int getBasePrice() {
        return basePrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceInfo priceInfo = (PriceInfo) o;
        return basePrice == priceInfo.basePrice &&
                discountPrice == priceInfo.discountPrice;
    }

    @Override
    public int hashCode() {
        return Objects.hash(basePrice, discountPrice);
    }
}
