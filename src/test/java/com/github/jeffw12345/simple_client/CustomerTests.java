package com.github.jeffw12345.simple_client;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerTests {

    @Test
    public void testToString() throws Exception {
        Customer customer = Customer.builder()
                .customerReference("001")
                .customerName("John Doe")
                .addressLine1("123 Elm Street")
                .addressLine2("")
                .town("Springfield")
                .county("Illinois")
                .country("USA")
                .postcode("62701")
                .build();

        String expectedJson = "{\n" +
                "  \"customerReference\" : \"001\",\n" +
                "  \"customerName\" : \"John Doe\",\n" +
                "  \"addressLine1\" : \"123 Elm Street\",\n" +
                "  \"addressLine2\" : \"\",\n" +
                "  \"town\" : \"Springfield\",\n" +
                "  \"county\" : \"Illinois\",\n" +
                "  \"country\" : \"USA\",\n" +
                "  \"postcode\" : \"62701\"\n" +
                "}";

        JsonNode expectedNode = new ObjectMapper().readTree(expectedJson);
        JsonNode actualNode = new ObjectMapper().readTree(customer.toJsonString());

        assertEquals(
                expectedNode,
                actualNode,
                "The JSON representation of the Customer object does not match the expected format."
        );
    }
}
