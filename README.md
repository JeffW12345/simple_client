# SIMPLE CLIENT
=============

## Overview

This application works in conjunction with the [Simple Server](https://github.com/JeffW12345/simple_server) application.

The Simple Client performs the following functions:

1. Converts the contents of a CSV file located in the `src/resources` folder into `Customer` objects.
2. Sends these `Customer` objects as JSON payloads to the `/customers` endpoint on a server running at `localhost:8080`, where they are stored in a database.

### Key Classes

- **Customer**: A model class representing customer data.
- **DataImportService**: Imports records from the CSV file and creates a `List<Customer>` objects.
- **PostService**: Iterates through the list of `Customer` objects and sends each one via a POST request to the server. The JSON representation of the `Customer` object is included in the request body.

### CSV File Location

The CSV file, named `customer_data.csv`, is located in the `src/resources` directory.

## Instructions

### Prerequisites

Before running this client application, ensure that the [Simple Server](https://github.com/JeffW12345/simple_server) is running on `localhost` at port `8080`.

### Running the Application

You can run this application by executing the `main` method in the `Main` class.

### Logging and Status Codes

As the client sends each record, the application will log the outcome:

- **200 OK**: The customer record was successfully sent and stored.
- **4xx**: A status code beginning with "4" indicates that something went wrong with the request. Common issues include invalid data or server-side validation errors.

### Troubleshooting

- Ensure the server is running before starting the client.
- Check that the CSV file `customer_data.csv` is correctly formatted and located in the `src/resources` directory.
- If you encounter a status code starting with "4", review the server logs for more details about the error.

