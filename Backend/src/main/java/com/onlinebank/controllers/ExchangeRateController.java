package com.onlinebank.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/exchange-rates")
@Tag(name = "Курсы валют", description = "Операции для получения курсов валют")
public class ExchangeRateController {

    private final String apiKey = "cf0e72f587c6b513182c7071";
    private final String url = "https://v6.exchangerate-api.com/v6/" + apiKey;
    private final RestTemplate restTemplate;

    @Autowired
    public ExchangeRateController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/latest/{currency}")
    @Operation(summary = "Получить последние курсы для базовой валюты", description = "Получает последние курсы для базовой валюты")
    @ApiResponse(responseCode = "200", description = "Успешно получены последние курсы", content = @Content(schema = @Schema(implementation = Object.class)))
    @ApiResponse(responseCode = "404", description = "Курсы для указанной валюты не найдены")
    public ResponseEntity<Object> getExchangeRatesFromBase(@PathVariable("currency") String currency) {
        try {
            return restTemplate.getForEntity(url + "/latest/" + currency, Object.class);
        } catch (HttpClientErrorException.NotFound notFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundException.getResponseBodyAs(Object.class));
        }
    }

    @GetMapping("/pair/{base_currency}/{target_currency}")
    @Operation(summary = "Получить курсы для пары валют", description = "Получает курсы для пары базовой и целевой валюты")
    @ApiResponse(responseCode = "200", description = "Успешно получены курсы для пары валют", content = @Content(schema = @Schema(implementation = Object.class)))
    @ApiResponse(responseCode = "404", description = "Курсы для указанной пары валют не найдены")
    public ResponseEntity<Object> getExchangeRatesFromBaseToTargetCurrency(@PathVariable("base_currency") String base_currency,
                                                                           @PathVariable("target_currency") String target_currency) {
        try {
            return restTemplate.getForEntity(url + "/pair/" + base_currency + "/" + target_currency, Object.class);
        } catch (HttpClientErrorException.NotFound notFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundException.getResponseBodyAs(Object.class));
        }
    }

    @GetMapping("/pair/{base_currency}/{target_currency}/{amount}")
    @Operation(summary = "Получить курсы для пары валют с указанной суммой", description = "Получает курсы для пары базовой и целевой валюты с указанной суммой")
    @ApiResponse(responseCode = "200", description = "Успешно получены курсы для пары валют с указанной суммой", content = @Content(schema = @Schema(implementation = Object.class)))
    @ApiResponse(responseCode = "404", description = "Курсы для указанной пары валют с указанной суммой не найдены")
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
    @Operation(summary = "Получить список поддерживаемых валют", description = "Получает список поддерживаемых валют")
    @ApiResponse(responseCode = "200", description = "Успешно получен список поддерживаемых валют", content = @Content(schema = @Schema(implementation = Object.class)))
    @ApiResponse(responseCode = "404", description = "Список поддерживаемых валют не найден")
    public ResponseEntity<Object> getSupport() {
        try {
            return restTemplate.getForEntity(url + "/codes", Object.class);
        } catch (HttpClientErrorException.NotFound notFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundException.getResponseBodyAs(Object.class));
        }
    }

    @GetMapping("/quota")
    @Operation(summary = "Получить информацию о квоте", description = "Получает информацию о квоте")
    @ApiResponse(responseCode = "200", description = "Успешно получена информация о квоте", content = @Content(schema = @Schema(implementation = Object.class)))
    @ApiResponse(responseCode = "404", description = "Информация о квоте не найдена")
    public ResponseEntity<Object> getQuota() {
        try {
            return restTemplate.getForEntity(url + "/quota", Object.class);
        } catch (HttpClientErrorException.NotFound notFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundException.getResponseBodyAs(Object.class));
        }
    }
}
