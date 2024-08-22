package com.github.jeffw12345.simple_client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class PostServiceTests {

    private PostService postService;
    private HttpClient mockHttpClient;
    private HttpResponse<String> mockResponse;

    @BeforeEach
    public void setUp() {
        try (AutoCloseable ignored = openMocks(this)) {
            mockHttpClient = mock(HttpClient.class);
            postService = new PostService(mockHttpClient);
            mockResponse = mock(HttpResponse.class, RETURNS_DEEP_STUBS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void givenTwoItemsInListPassedToPostCustomer_thenHttpResponseSendMethodCalledTwice() throws Exception {
        Customer customer1 = Customer.builder()
                .customerReference("001")
                .customerName("John Doe")
                .addressLine1("123 Elm Street")
                .addressLine2("")
                .town("Springfield")
                .county("Illinois")
                .country("USA")
                .postcode("62701")
                .build();

        Customer customer2 = Customer.builder()
                .customerReference("002")
                .customerName("Jane Doe")
                .addressLine1("123 Elm Street")
                .addressLine2("")
                .town("Springfield")
                .county("Illinois")
                .country("USA")
                .postcode("62701")
                .build();

        HttpResponse<String> mockResponse = mock(HttpResponse.class, RETURNS_DEEP_STUBS);
        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString()))).thenReturn(mockResponse);
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("Success");

        postService.postCustomerList(List.of(customer1, customer2));

        int numberOfTimesExpectedToBeCalled = 2;
        verify(mockHttpClient, times(numberOfTimesExpectedToBeCalled))
                .send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString()));
    }

    @Test
    public void givenListPassedToPostCustomerList_thenHttpRequestSettingsAsExpected() throws IOException, InterruptedException {
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

        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("Success");
        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(mockResponse);

        PostService postService = new PostService(mockHttpClient);

        postService.postCustomerList(Collections.singletonList(customer));

        ArgumentCaptor<HttpRequest> requestCaptor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(mockHttpClient).send(requestCaptor.capture(), eq(HttpResponse.BodyHandlers.ofString()));

        HttpRequest capturedRequest = requestCaptor.getValue();
        assertEquals(URI.create("http://localhost:8080/customers"), capturedRequest.uri());

        String expectedContentType = "application/json";
        String actualContentType = capturedRequest.headers().firstValue("Content-Type")
                .orElseThrow(() -> new IllegalStateException("Content-Type header is missing"));
        assertEquals(expectedContentType, actualContentType);

        String expectedHttpVerb = "POST";
        String actualHttpVerb = capturedRequest.method();
        assertEquals(expectedHttpVerb, actualHttpVerb);
    }

    @Test
    public void givenEmptyListPassedToPostCustomerList_thenExpectedExceptionThrown() {
        List<Customer> emptyCustomerList = Collections.emptyList();
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> postService.postCustomerList(emptyCustomerList));
        Assertions.assertEquals("List of Customers empty", exception.getMessage());
    }
}
