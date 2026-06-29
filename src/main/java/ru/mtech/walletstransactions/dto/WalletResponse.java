package ru.mtech.walletstransactions.dto;

import java.math.BigDecimal;

public class WalletResponse {

    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private String currency;

    public WalletResponse(Long id, String accountNumber, BigDecimal balance, String currency) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getCurrency() {
        return currency;
    }
}
