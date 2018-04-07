package com.supermarket.checkout.pricing;

import com.supermarket.checkout.domain.PricingRule;

import java.util.Collection;


public interface PricingRulesReader {
    Collection<PricingRule> fetchPricingRules();
}
