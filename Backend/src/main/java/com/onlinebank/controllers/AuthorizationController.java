package com.onlinebank.controllers;

import com.onlinebank.dto.request.AuthorizationRequest;
import com.onlinebank.dto.request.CustomerRequest;
import com.onlinebank.dto.response.AccountResponse;
import com.onlinebank.dto.response.AuthorizationResponse;
import com.onlinebank.security.JWTUtil;
import com.onlinebank.services.CustomerService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Denis Durbalov
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "", description = "")
public class AuthorizationController {

    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;

    private final CustomerService customerService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthorizationController(JWTUtil jwtUtil, ModelMapper modelMapper, CustomerService customerService, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.customerService = customerService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup")
    @Operation(summary = "Регистрация", description = "Регистрация нового пользователя")
    @ApiResponse(responseCode = "201", description = "Пользователь успешно зарегистрирован", content = @Content(schema = @Schema(implementation = AccountResponse.class)))
    @ApiResponse(responseCode = "400", description = "Ошибка валидации входных данных")
    public ResponseEntity<Object> signup(@RequestBody @Valid CustomerRequest customerRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : fieldErrors) {
                errors.add(fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        if (customerService.existCustomer(customerRequest.getUsername()))
            return ResponseEntity.badRequest().body("User with username " + customerRequest.getUsername() + " already exist");
        customerService.saveCustomer(customerRequest.toCustomer());
        String token = jwtUtil.generateToken(customerRequest.getUsername());
        return ResponseEntity.ok().body(new AuthorizationResponse(token));
    }

    @PostMapping("signin")
    @Operation(summary = "Вход", description = "Авторизация пользователя по данным входа")
    @ApiResponse(responseCode = "201", description = "Успешная авторизация", content = @Content(schema = @Schema(implementation = AccountResponse.class)))
    @ApiResponse(responseCode = "400", description = "Ошибка валидации входных данных или неверные учетные данные")
    public ResponseEntity<Object> signin(@RequestBody @Valid AuthorizationRequest authorizationRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : fieldErrors) {
                errors.add(fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authorizationRequest.getUsername(),
                        authorizationRequest.getPassword());
        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Incorrect credentials");
        }
        String token = jwtUtil.generateToken(authorizationRequest.getUsername());
        return ResponseEntity.ok().body(new AuthorizationResponse(token));
    }
}
