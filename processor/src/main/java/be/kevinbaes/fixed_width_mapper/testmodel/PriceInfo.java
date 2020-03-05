package be.kevinbaes.fixed_width_mapper.testmodel;

public class PriceInfo {
    private int basePrice;
    private int discountPrice;

    public PriceInfo(int basePrice, int discountPrice) {
        this.basePrice = basePrice;
        this.discountPrice = discountPrice;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getBasePrice() {
        return basePrice;
    }
}
