package com.commercial.commerce.sale.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.commercial.commerce.UserAuth.Models.User;
import com.commercial.commerce.UserAuth.Service.AuthService;
import com.commercial.commerce.UserAuth.Service.RefreshTokenService;
// import com.commercial.commerce.UserAuth.Service.RefreshTokenService;
import com.commercial.commerce.response.ApiResponse;
import com.commercial.commerce.response.Status;
import com.commercial.commerce.sale.entity.ModelEntity;
import com.commercial.commerce.sale.entity.TransactionEntity;
import com.commercial.commerce.sale.service.TransactionService;
import com.commercial.commerce.sale.utils.Statistique;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/bibine")
@AllArgsConstructor
public class TransactionController extends Controller {

    private final TransactionService transactionService;
    private final RefreshTokenService refreshTokenService;
    private final AuthService service;

    @GetMapping("/transactions")
    public ResponseEntity<ApiResponse<List<TransactionEntity>>> getAllTransactionsById() {
        try {
            List<TransactionEntity> transactions = transactionService.getAllTransactions();
            return createResponseEntity(transactions, "Transactions retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<ApiResponse<TransactionEntity>> getTransactionById(@PathVariable String id) {
        try {
            TransactionEntity transaction = transactionService.getById(id).get();
            return createResponseEntity(transaction, "Transaction retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/pagination/transactions")
    public ResponseEntity<ApiResponse<List<TransactionEntity>>> getAllTransactionsWithPagination(
            @RequestParam(name = "offset") int id,
            @RequestParam(name = "limit", defaultValue = "5") int limit) {
        try {
            List<TransactionEntity> transactions = transactionService.selectWithPagination(id, limit);
            return createResponseEntity(transactions, "Transactions retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PutMapping("/valid/transactions/{id}")
    public ResponseEntity<ApiResponse<TransactionEntity>> valideTransaction(@PathVariable String id) {
        try {
            TransactionEntity transaction = transactionService.getById(id).get();
            transactionService.updateState(transaction, 2);
            return createResponseEntity(transaction, "Transaction updated successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PutMapping("/unvalid/transactions/{id}")
    public ResponseEntity<ApiResponse<TransactionEntity>> unvalideTransaction(@PathVariable String id) {
        try {
            TransactionEntity transaction = transactionService.getById(id).get();
            transactionService.updateState(transaction, 0);
            return createResponseEntity(transaction, "Transaction updated successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @GetMapping("/transactions/pagination")
    public ResponseEntity<ApiResponse<Long>> getPagination(
            @RequestParam(name = "limit", defaultValue = "5") int limit) {
        try {

            return createResponseEntity(transactionService.pagination(limit), "Categories retrieved successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }

    @PostMapping("/user/{iduser}/recharge")
    public ResponseEntity<ApiResponse<User>> recharge(@PathVariable Long iduser,
            @RequestParam(name = "montant") double montant,
            HttpServletRequest request) {
        try {
            if (this.isTokenValid(refreshTokenService.splitToken(request.getHeader("Authorization")),
                    iduser) == false) {
                ResponseEntity.ok(Status.builder().status("error").details("not the user").build());
            }
            return createResponseEntity(service.recharge(iduser, montant).get(), "Compte updated successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }

    }

    @GetMapping("/statistique/transactions/sent")
    public ResponseEntity<ApiResponse<List<Statistique>>> countSoldCarsTypes() {
        try {

            return createResponseEntity(transactionService.statistiques(),
                    "Annonces retrieved successfully for the given state");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, new Status("error", e.getMessage()), LocalDateTime.now()));
        }
    }
}
