package com.supermarket.checkout.pricing;

import com.supermarket.checkout.domain.PricingRule;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class ItemPriceCalculator implements PriceCalculator {
    private final PricingRulesReader pricingRulesReader;

    public ItemPriceCalculator(PricingRulesReader pricingRulesReader) {
        this.pricingRulesReader = pricingRulesReader;
    }

    @Override
    public BigDecimal calculate(List<String> skuIds) {
        // Step 1: Read pricing rules and create a map of SKUID, Pricing rule
        Collection<PricingRule> items = pricingRulesReader.fetchPricingRules();
        Map<String, PricingRule> pricingRuleMap = items.stream().collect(Collectors.toMap(PricingRule::getSkuId, Function.identity()));

        // Step 2: Map of SKUID and number of items for the SKUID
        Map<String, Long> itemsSkuAndQuantity = skuIds.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // Step 3: Calculate total price of items based on pricing rule and the number of items in the list
        return itemsSkuAndQuantity.entrySet().stream().reduce(BigDecimal.ZERO, (currentSum, entry) ->
                currentSum.add(pricingRuleMap.get(entry.getKey()).calculatePriceFor(entry.getValue())), BigDecimal::add);
    }
}
