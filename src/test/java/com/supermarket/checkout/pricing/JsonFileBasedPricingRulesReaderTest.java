package com.supermarket.checkout.pricing;

import com.supermarket.checkout.domain.PricingRule;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

import static org.junit.Assert.assertEquals;


public class JsonFileBasedPricingRulesReaderTest {

    @Test
    public void testFetchPricingRulesReturnsCollectionOfRules() throws URISyntaxException {
        URI pricingRulesURI = getClass().getResource("/testpricingrulesjson.txt").toURI();
        PricingRulesReader pricingRulesReader = new JsonFileBasedPricingRulesReader(pricingRulesURI);
        Collection<PricingRule> pricingRules = pricingRulesReader.fetchPricingRules();
        assertEquals(4, pricingRules.size());
    }

    @Test
    public void testFetchPricingRulesThrowsExceptionWhenFileCannotBeRead() throws URISyntaxException {
        PricingRulesReader pricingRulesReader = new JsonFileBasedPricingRulesReader("/unknown.txt");
        Collection<PricingRule> pricingRules = pricingRulesReader.fetchPricingRules();
        assertEquals(0, pricingRules.size());
    }
}
