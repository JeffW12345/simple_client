package com.github.jeffw12345.simple_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

@Slf4j
public class DataImportService {

    public List<Customer> importCustomers(Path path) {
        List<Customer> list = List.of();
        try {
            int initialCapacity = (int) Files.lines(path).count();
            list = new ArrayList<>(initialCapacity);

            BufferedReader reader = Files.newBufferedReader(path);
            Iterable<CSVRecord> records = CSVFormat.RFC4180.builder()
                    .setHeader().setSkipHeaderRecord(true)
                    .build()
                    .parse(reader);

            for (CSVRecord record : records) {
                Customer customer = Customer.builder()
                        .customerReference(record.get("Customer Ref"))
                        .customerName(record.get("Customer Name"))
                        .addressLine1(record.get("Address Line 1"))
                        .addressLine2(record.get("Address Line 2"))
                        .town(record.get("Town"))
                        .county(record.get("County"))
                        .country(record.get("Country"))
                        .postcode(record.get("Postcode"))
                        .build();
                list.add(customer);
            }
        } catch (IOException e) {
            log.error("IO exception: " + e);
        }
        return list;
    }
}
