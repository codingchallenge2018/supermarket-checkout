package com.supermarket.checkout.pricing;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class ItemPriceCalculatorTest {
    private PriceCalculator priceCalculator;

    private List<String> items;

    private BigDecimal expectedPrice;

    public ItemPriceCalculatorTest(List<String> items, BigDecimal expectedPrice) {
        this.items = items;
        this.expectedPrice = expectedPrice;
    }

    @Parameterized.Parameters(name = "{index}: price({0})={1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                //Single item purchased. Not eligible for discount
                {Collections.singletonList("A"), new BigDecimal(50)},
                //Multiple items purchased but not eligible for discount
                {Arrays.asList("A", "B"), new BigDecimal(80)},
                //Multiple items purchased with both items eligible for multibuy discount
                {Arrays.asList("A", "B", "A", "A", "B"), new BigDecimal(175)},
                //Multiple items purchased with one of the items eligible for multibuy discount
                {Arrays.asList("A", "B", "B", "C", "D"), new BigDecimal(155)}
        });
    }

    @Before
    public void setUp() throws URISyntaxException {
        URI pricingRulesURI = getClass().getResource("/testpricingrulesjson.txt").toURI();
        PricingRulesReader pricingRulesReader = new JsonFileBasedPricingRulesReader(pricingRulesURI);
        priceCalculator = new ItemPriceCalculator(pricingRulesReader);
    }

    @Test
    public void testCalculatePrice() {
        assertEquals(expectedPrice, priceCalculator.calculate(items));
    }
}
