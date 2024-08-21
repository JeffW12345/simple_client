package com.github.jeffw12345.simple_client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Customer {
    private String customerReference;
    private String customerName;
    private String addressLine1;
    private String addressLine2;
    private String town;
    private String county;
    private String country;
    private String postcode;

    public String toJsonString() {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return objectWriter.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize customer to JSON", e);
        }
    }
}