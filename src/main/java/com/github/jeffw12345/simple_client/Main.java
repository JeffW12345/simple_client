package com.github.jeffw12345.simple_client;

import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Path path = getPath();
        List<Customer> importedCustomers = new DataImportService().importCustomers(path);
        new PostService(HttpClient.newHttpClient()).postCustomerList(importedCustomers);
    }

    private static Path getPath() {
        URL resource = Main.class.getClassLoader().getResource("customer_data.csv");
        if (resource == null) {
            throw new RuntimeException("Resource not found");
        }
        try {
            return Paths.get(resource.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException("URI syntax error", e);
        }
    }
}
