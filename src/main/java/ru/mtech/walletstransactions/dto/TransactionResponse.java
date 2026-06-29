package ru.mtech.walletstransactions.dto;

import ru.mtech.walletstransactions.entity.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponse {

    private Long id;
    private Long fromWalletId;
    private Long toWalletId;
    private BigDecimal amount;
    private TransactionType type;
    private LocalDateTime createdAt;

    public TransactionResponse(Long id, Long fromWalletId, Long toWalletId, BigDecimal amount,
                               TransactionType type, LocalDateTime createdAt) {
        this.id = id;
        this.fromWalletId = fromWalletId;
        this.toWalletId = toWalletId;
        this.amount = amount;
        this.type = type;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getFromWalletId() {
        return fromWalletId;
    }

    public Long getToWalletId() {
        return toWalletId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
