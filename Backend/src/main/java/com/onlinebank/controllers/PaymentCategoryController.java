package com.onlinebank.controllers;

import com.onlinebank.dto.request.PaymentCategoryRequest;
import com.onlinebank.dto.response.PaymentCategoryResponse;
import com.onlinebank.models.PaymentCategoryModel;
import com.onlinebank.services.PaymentCategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Denis Durbalov
 */
@RestController
@RequestMapping("/payment-categories")
@Tag(name = "Категории оплат", description = "Операции связанные с категориями оплат")
public class PaymentCategoryController {

    private final PaymentCategoryService paymentCategoryService;

    @Autowired
    public PaymentCategoryController(PaymentCategoryService paymentCategoryService) {
        this.paymentCategoryService = paymentCategoryService;
    }

    @GetMapping
    @Operation(summary = "Получить все категории платежей", description = "Получает список всех категорий платежей")
    @ApiResponse(responseCode = "200", description = "Список категорий платежей успешно получен", content = @Content(schema = @Schema(implementation = PaymentCategoryResponse.class)))
    public List<PaymentCategoryResponse> getPaymentCategories() {
        List<PaymentCategoryModel> paymentCategories = paymentCategoryService.getAllPaymentCategories();
        List<PaymentCategoryResponse> paymentCategoryResponses = new ArrayList<>();
        for (PaymentCategoryModel paymentCategoryModel : paymentCategories) {
            paymentCategoryResponses.add(new PaymentCategoryResponse(paymentCategoryModel));
        }
        return paymentCategoryResponses;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить категорию платежа по ID", description = "Получает информацию о категории платежа по ее уникальному ID")
    @ApiResponse(responseCode = "200", description = "Категория платежа успешно получена", content = @Content(schema = @Schema(implementation = PaymentCategoryResponse.class)))
    @ApiResponse(responseCode = "404", description = "Категория платежа не найдена")
    public ResponseEntity<Object> getPaymentCategory(@PathVariable("id") int id) {
        PaymentCategoryModel paymentCategoryModel = paymentCategoryService.getPaymentCategoryById(id);
        if (paymentCategoryModel != null) {
            return ResponseEntity.ok(new PaymentCategoryResponse(paymentCategoryModel));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment category with id " + id + " not found");
        }
    }

    @PostMapping
    @Operation(summary = "Создать категорию платежа", description = "Создает новую категорию платежа")
    @ApiResponse(responseCode = "201", description = "Категория платежа успешно создана", content = @Content(schema = @Schema(implementation = PaymentCategoryResponse.class)))
    @ApiResponse(responseCode = "400", description = "Ошибка в запросе")
    public ResponseEntity<Object> createPaymentCategory(@RequestBody @Valid PaymentCategoryRequest paymentCategoryRequest,
                                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : fieldErrors) {
                errors.add(fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        PaymentCategoryModel paymentCategoryModel = paymentCategoryRequest.toPaymentCategory();
        paymentCategoryService.savePaymentCategory(paymentCategoryModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(new PaymentCategoryResponse(paymentCategoryModel));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить категорию платежа", description = "Удаляет категорию платежа по ее уникальному ID")
    @ApiResponse(responseCode = "200", description = "Категория платежа успешно удалена")
    @ApiResponse(responseCode = "404", description = "Категория платежа не найдена")
    public ResponseEntity<Object> deletePaymentCategory(@PathVariable("id") int id) {
        PaymentCategoryModel paymentCategoryModel = paymentCategoryService.getPaymentCategoryById(id);
        if (paymentCategoryModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment category with id " + id + " not found");
        }

        paymentCategoryService.deletePaymentCategoryById(id);
        return ResponseEntity.ok("Payment category with id " + id + " has been deleted successfully.");
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить информацию о категории платежа", description = "Обновляет информацию о категории платежа по ее уникальному ID")
    @ApiResponse(responseCode = "200", description = "Информация о категории платежа успешно обновлена", content = @Content(schema = @Schema(implementation = PaymentCategoryResponse.class)))
    @ApiResponse(responseCode = "400", description = "Ошибка в запросе")
    @ApiResponse(responseCode = "404", description = "Категория платежа не найдена")
    public ResponseEntity<Object> updatePaymentCategory(@PathVariable("id") int id,
                                                        @RequestBody @Valid PaymentCategoryRequest paymentCategoryRequest,
                                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : fieldErrors) {
                errors.add(fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        PaymentCategoryModel existingPaymentCategoryModel = paymentCategoryService.getPaymentCategoryById(id);
        if (existingPaymentCategoryModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment category with id " + id + " not found");
        }

        existingPaymentCategoryModel = paymentCategoryRequest.toPaymentCategory();
        existingPaymentCategoryModel.setId(id);

        paymentCategoryService.savePaymentCategory(existingPaymentCategoryModel);
        return ResponseEntity.ok(new PaymentCategoryResponse(existingPaymentCategoryModel));
    }
}
