package com.supermarket.checkout.domain;

import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;


public class PricingRule {
    private final String skuId;
    private final BigDecimal unitPrice;
    private final Long multibuyCount;
    private final BigDecimal multibuyPrice;

    public PricingRule(String skuId, BigDecimal unitPrice, Long multibuyCount, BigDecimal multibuyPrice) {
        this.skuId = requireNonNull(skuId);
        this.unitPrice = requireNonNull(unitPrice);
        this.multibuyCount = multibuyCount;
        this.multibuyPrice = multibuyPrice;
    }

    /***
     * Calculates price of the item based on the pricing rule and quantity.
     * Total Price = (Multibuy Price * Multibuy Multiplier) + (Unit price * No of Items eligible for unit price)
     * @param quantity
     * @return Total price
     */
    public BigDecimal calculatePriceFor(Long quantity) {
        if (multibuyCount != null) {
            Long multibuyMultiplier = quantity / multibuyCount;
            Long remainingItemsToApplyUnitPrice = quantity % multibuyCount;
            return multibuyPrice.multiply(new BigDecimal(multibuyMultiplier))
                    .add(unitPrice.multiply(new BigDecimal(remainingItemsToApplyUnitPrice)));
        } else {
            return unitPrice.multiply(new BigDecimal(quantity));
        }
    }

    public String getSkuId() {
        return skuId;
    }
}
