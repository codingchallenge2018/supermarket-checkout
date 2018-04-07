package com.supermarket.checkout;

import com.supermarket.checkout.pricing.ItemPriceCalculator;
import com.supermarket.checkout.pricing.JsonFileBasedPricingRulesReader;
import com.supermarket.checkout.pricing.PricingRulesReader;

import java.math.BigDecimal;
import java.util.stream.Collectors;


public class ShoppingCart {

    public static void main(String[] args) {
        if (args.length != 2) {
            printUsage();
        }

        String itemSkuIds = args[0];
        String pricingRulesFile = args[1];

        PricingRulesReader pricingRuleReader = new JsonFileBasedPricingRulesReader(pricingRulesFile);
        ItemPriceCalculator itemPriceCalculator = new ItemPriceCalculator(pricingRuleReader);
        BigDecimal price = itemPriceCalculator.calculate(itemSkuIds.chars().mapToObj(i -> "" + (char) i).collect(Collectors.toList()));
        System.out.println("Price = [" + price + "]");
    }

    private static void printUsage() {
        System.err.println("Please run this program with 2 arguments");
        System.err.println("Example: ShoppingCart ABCBCD ruleFileAbsolutePathJson");
        System.err.println("Where ABCBCD is the item skuIds in any order");
        System.err.println("ruleFileAbsolutePathJson is absolute path of the pricing rules file in JSON format");
        System.exit(1);
    }
}
