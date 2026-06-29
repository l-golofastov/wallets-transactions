package ru.mtech.walletstransactions.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mtech.walletstransactions.dto.BalanceReportResponse;
import ru.mtech.walletstransactions.dto.SummaryReportResponse;
import ru.mtech.walletstransactions.dto.TransactionResponse;
import ru.mtech.walletstransactions.dto.WalletResponse;
import ru.mtech.walletstransactions.entity.Transaction;
import ru.mtech.walletstransactions.entity.Wallet;
import ru.mtech.walletstransactions.exception.WalletNotFoundException;
import ru.mtech.walletstransactions.repository.TransactionRepository;
import ru.mtech.walletstransactions.repository.WalletRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    public ReportService(WalletRepository walletRepository, TransactionRepository transactionRepository) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional(readOnly = true)
    public BalanceReportResponse getBalanceReport(Long walletId) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId));

        List<TransactionResponse> transactions = transactionRepository
                .findByFromWalletIdOrToWalletIdOrderByCreatedAtDesc(walletId, walletId)
                .stream()
                .map(this::toTransactionResponse)
                .toList();

        return new BalanceReportResponse(toWalletResponse(wallet), transactions);
    }

    @Transactional(readOnly = true)
    public SummaryReportResponse getSummaryReport() {
        BigDecimal totalAmount = walletRepository.findAll()
                .stream()
                .map(Wallet::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime startOfNextDay = startOfDay.plusDays(1);

        long successfulTransactionsToday = transactionRepository
                .countByCreatedAtGreaterThanEqualAndCreatedAtLessThan(startOfDay, startOfNextDay);

        return new SummaryReportResponse(totalAmount, successfulTransactionsToday);
    }

    private WalletResponse toWalletResponse(Wallet wallet) {
        return new WalletResponse(
                wallet.getId(),
                wallet.getAccountNumber(),
                wallet.getBalance(),
                wallet.getCurrency()
        );
    }

    private TransactionResponse toTransactionResponse(Transaction transaction) {
        Long fromWalletId = null;

        if (transaction.getFromWallet() != null) {
            fromWalletId = transaction.getFromWallet().getId();
        }

        return new TransactionResponse(
                transaction.getId(),
                fromWalletId,
                transaction.getToWallet().getId(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getCreatedAt()
        );
    }
}
