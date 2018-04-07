package com.supermarket.checkout.pricing;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.supermarket.checkout.domain.PricingRule;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;


public class JsonFileBasedPricingRulesReader implements PricingRulesReader {

    private static final Logger LOGGER = Logger.getLogger(JsonFileBasedPricingRulesReader.class.getName());
    private final URI pricingRulesURI;
    private final Gson gson = new Gson();
    public JsonFileBasedPricingRulesReader(String pricingRulesFile) {
        this(new File(pricingRulesFile).toURI());
    }

    public JsonFileBasedPricingRulesReader(URI pricingRulesURI) {
        this.pricingRulesURI = pricingRulesURI;
    }

    @Override
    public Collection<PricingRule> fetchPricingRules() {
        URI uri = new File(pricingRulesURI).toURI();
        try {
            return gson.fromJson(new String(Files.readAllBytes(Paths.get(uri))), new TypeToken<List<PricingRule>>(){}.getType());
        } catch (IOException e) {
            LOGGER.severe("Could not parse Json rules from file : " + pricingRulesURI + " Error : " + e.getMessage());
            LOGGER.severe(getExceptionAsString(e));
            return Collections.emptyList();
        }
    }

    private static String getExceptionAsString(Throwable e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
