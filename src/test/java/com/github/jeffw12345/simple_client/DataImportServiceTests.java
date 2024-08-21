package com.github.jeffw12345.simple_client;

import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DataImportServiceTests {
    Path filePath = getPath();

    @Test
    public void givenImportCustomersCalled_thenReturnsSpreadsheetWithExpectedNumberOfEntries() {
        DataImportService service = new DataImportService();
        List<Customer> customerRecords = service.importCustomers(filePath);

        int expectedSize = 3;
        assertEquals(expectedSize, customerRecords.size(), "Size of customer records should match expected size");
    }

    private Path getPath() {
        URL resource = getClass().getClassLoader().getResource("customer_spreadsheet_for_testing.csv");
        if (resource == null) {
            throw new RuntimeException("Resource not found");
        }
        Path filePath;
        try {
            filePath = Paths.get(resource.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return filePath;
    }
}