package com.currency.converter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "app.security")
public class ApiKeyProperties {

    private Map<String, String> apiKeys = new HashMap<>();

    public Map<String, String> getApiKeys() {
        return apiKeys;
    }

    public void setApiKeys(Map<String, String> apiKeys) {
        this.apiKeys = apiKeys;
    }

    public boolean isValid(String apiKey) {
        return apiKey != null && apiKeys.containsKey(apiKey);
    }

    public String ownerOf(String apiKey) {
        return apiKeys.getOrDefault(apiKey, "Unknown User");
    }
}
