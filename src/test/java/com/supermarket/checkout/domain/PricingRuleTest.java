package com.supermarket.checkout.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class PricingRuleTest {
    private PricingRule pricingRule;

    private Long quantity;

    private BigDecimal expectedPrice;

    public PricingRuleTest(PricingRule pricingRule, Long quantity, BigDecimal expectedPrice) {
        this.pricingRule = pricingRule;
        this.quantity = quantity;
        this.expectedPrice = expectedPrice;
    }

    @Parameterized.Parameters(name = "{index}: price({1})={2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {createRule("A", 50, 3L, 130L), 4L, new BigDecimal(180)},
                {createRule("A", 50, 3L, 130L), 1L, new BigDecimal(50)},
                {createRule("A", 50, 3L, 130L), 0L, BigDecimal.ZERO},
                {new PricingRule("A", new BigDecimal(50), null, null), 4L, new BigDecimal(200)},
        });
    }

    @Test
    public void testCalculatePrice() {
        assertEquals(expectedPrice, pricingRule.calculatePriceFor(quantity));
    }

    public static PricingRule createRule(String skuId, long unitPrice, Long multibuyCount, Long multibuyPrice) {
        return new PricingRule(skuId, new BigDecimal(unitPrice), multibuyCount, new BigDecimal(multibuyPrice));
    }
}
