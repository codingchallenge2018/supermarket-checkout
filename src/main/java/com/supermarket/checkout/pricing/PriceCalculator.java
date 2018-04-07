package com.supermarket.checkout.pricing;

import java.math.BigDecimal;
import java.util.List;


public interface PriceCalculator {
    BigDecimal calculate(List<String> items);
}
