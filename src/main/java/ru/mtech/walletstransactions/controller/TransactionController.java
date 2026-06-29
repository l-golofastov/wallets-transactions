package ru.mtech.walletstransactions.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.mtech.walletstransactions.dto.CreateTransactionRequest;
import ru.mtech.walletstransactions.dto.TransactionResponse;
import ru.mtech.walletstransactions.entity.Transaction;
import ru.mtech.walletstransactions.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse createTransaction(@Valid @RequestBody CreateTransactionRequest request) {
        Transaction transaction = transactionService.createTransaction(request);

        return new TransactionResponse(
                transaction.getId(),
                transaction.getFromWallet().getId(),
                transaction.getToWallet().getId(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getCreatedAt()
        );
    }
}
