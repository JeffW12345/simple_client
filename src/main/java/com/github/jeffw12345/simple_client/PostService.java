package com.github.jeffw12345.simple_client;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Slf4j
public class PostService {
    private static final String POST_URL = "http://localhost:8080/customers";
    private final HttpClient httpClient;

    public PostService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void postCustomerList(List<Customer> customerObjectsToDispatch) {
        for (Customer customer : customerObjectsToDispatch) {
            postCustomer(customer);
        }
    }

    private void postCustomer(Customer customer) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(POST_URL))
                    .POST(HttpRequest.BodyPublishers.ofString(customer.toJsonString()))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            log.info("Response Code: {}", response.statusCode());
            log.info("Response Body: {}", response.body());
        } catch (IOException | InterruptedException e) {
            log.error("Failed to post customer: {}", customer, e);
            Thread.currentThread().interrupt();
        }
    }
}
