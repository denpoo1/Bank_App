package com.onlinebank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/exchange-rates")
public class ExchangeRateController {

    private final String apiKey = "cf0e72f587c6b513182c7071";
    private final String url = "https://v6.exchangerate-api.com/v6/" + apiKey;
    private final RestTemplate restTemplate;

    @Autowired
    public ExchangeRateController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/latest/{currency}")
    public ResponseEntity<Object> getExchangeRatesFromBase(@PathVariable("currency") String currency) {
        try {
            return restTemplate.getForEntity(url + "/latest/" + currency, Object.class);
        } catch (HttpClientErrorException.NotFound notFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundException.getResponseBodyAs(Object.class));
        }
    }

    @GetMapping("/pair/{base_currency}/{target_currency}")
    public ResponseEntity<Object> getExchangeRatesFromBaseToTargetCurrency(@PathVariable("base_currency") String base_currency,
                                                                           @PathVariable("target_currency") String target_currency) {
        try {
            return restTemplate.getForEntity(url + "/pair/" + base_currency + "/" + target_currency, Object.class);
        } catch (HttpClientErrorException.NotFound notFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundException.getResponseBodyAs(Object.class));
        }
    }

    @GetMapping("/pair/{base_currency}/{target_currency}/{amount}")
    public ResponseEntity<Object> getExchangeRatesFromBaseToTargetCurrencyWithAmount(@PathVariable("base_currency") String base_currency,
                                                                                     @PathVariable("target_currency") String target_currency,
                                                                                     @PathVariable("amount") double amount) {
        try {
            return restTemplate.getForEntity(url + "/pair/" + base_currency + "/" + target_currency + "/" + amount, Object.class);
        } catch (HttpClientErrorException.NotFound notFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundException.getResponseBodyAs(Object.class));
        }
    }

    @GetMapping("/support")
    public ResponseEntity<Object> getSupport() {
        try {
            return restTemplate.getForEntity(url + "/codes", Object.class);
        } catch (HttpClientErrorException.NotFound notFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundException.getResponseBodyAs(Object.class));
        }
    }

    @GetMapping("/quota")
    public ResponseEntity<Object> getQuota() {
        try {
            return restTemplate.getForEntity(url + "/quota", Object.class);
        } catch (HttpClientErrorException.NotFound notFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundException.getResponseBodyAs(Object.class));
        }
    }
}
