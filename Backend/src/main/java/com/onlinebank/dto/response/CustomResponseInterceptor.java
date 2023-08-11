package com.onlinebank.dto.response;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.List;

public class CustomResponseInterceptor implements ClientHttpRequestInterceptor {

    private final List<String> headersToRemove;

    public CustomResponseInterceptor(List<String> headersToRemove) {
        this.headersToRemove = headersToRemove;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        ClientHttpResponse response = execution.execute(request, body);
        response.getHeaders().remove("Access-Control-Allow-Origin");
        return response;
    }
}
