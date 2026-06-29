package ru.mtech.walletstransactions.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mtech.walletstransactions.dto.CreateTransactionRequest;
import ru.mtech.walletstransactions.entity.Transaction;
import ru.mtech.walletstransactions.entity.TransactionType;
import ru.mtech.walletstransactions.entity.Wallet;
import ru.mtech.walletstransactions.exception.BadRequestException;
import ru.mtech.walletstransactions.exception.NotEnoughMoneyException;
import ru.mtech.walletstransactions.exception.WalletNotFoundException;
import ru.mtech.walletstransactions.repository.TransactionRepository;
import ru.mtech.walletstransactions.repository.WalletRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(WalletRepository walletRepository, TransactionRepository transactionRepository) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public Transaction createTransaction(CreateTransactionRequest request) {
        validateRequest(request);

        log.info("Start payment from wallet {} to wallet {}, amount {}",
                request.getFromWalletId(), request.getToWalletId(), request.getAmount());

        Wallet fromWallet = walletRepository.findById(request.getFromWalletId())
                .orElseThrow(() -> new WalletNotFoundException(request.getFromWalletId()));

        Wallet toWallet = walletRepository.findById(request.getToWalletId())
                .orElseThrow(() -> new WalletNotFoundException(request.getToWalletId()));

        if (fromWallet.getBalance().compareTo(request.getAmount()) < 0) {
            log.info("Payment rejected: not enough money on wallet {}", fromWallet.getId());
            throw new NotEnoughMoneyException();
        }

        fromWallet.setBalance(fromWallet.getBalance().subtract(request.getAmount()));
        toWallet.setBalance(toWallet.getBalance().add(request.getAmount()));

        walletRepository.save(fromWallet);
        walletRepository.save(toWallet);

        Transaction transaction = new Transaction(
                fromWallet,
                toWallet,
                request.getAmount(),
                TransactionType.PAYMENT,
                LocalDateTime.now()
        );

        Transaction savedTransaction = transactionRepository.save(transaction);

        log.info("Payment completed, transaction id {}", savedTransaction.getId());

        return savedTransaction;
    }

    private void validateRequest(CreateTransactionRequest request) {
        if (request == null) {
            throw new BadRequestException("Request body is required");
        }

        if (request.getFromWalletId() == null) {
            throw new BadRequestException("fromWalletId is required");
        }

        if (request.getToWalletId() == null) {
            throw new BadRequestException("toWalletId is required");
        }

        BigDecimal amount = request.getAmount();

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("amount must be positive");
        }
    }
}
