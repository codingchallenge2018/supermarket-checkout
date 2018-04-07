package com.supermarket.checkout.pricing;

import com.supermarket.checkout.domain.PricingRule;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;


public class ItemPriceCalculator implements PriceCalculator {
    private final PricingRulesReader pricingRulesReader;

    public ItemPriceCalculator(PricingRulesReader pricingRulesReader) {
        this.pricingRulesReader = pricingRulesReader;
    }

    @Override
    public BigDecimal calculate(List<String> skuIds) {
        Collection<PricingRule> items = pricingRulesReader.fetchPricingRules();
        Map<String, PricingRule> pricingRuleMap = items.stream().collect(Collectors.toMap(PricingRule::getSkuId, Function.identity()));

        Map<String, Long> itemsSkuAndQuantity = skuIds.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        BiFunction<BigDecimal, Map.Entry<String, Long>, BigDecimal> f = (currentSum, entry) ->
                currentSum.add(pricingRuleMap.get(entry.getKey()).calculatePriceFor(entry.getValue()));
        return itemsSkuAndQuantity.entrySet().stream().reduce(BigDecimal.ZERO, f, BigDecimal::add);
    }
}
