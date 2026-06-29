package ru.mtech.walletstransactions.dto;

import java.util.List;

public class BalanceReportResponse {

    private WalletResponse wallet;
    private List<TransactionResponse> transactions;

    public BalanceReportResponse(WalletResponse wallet, List<TransactionResponse> transactions) {
        this.wallet = wallet;
        this.transactions = transactions;
    }

    public WalletResponse getWallet() {
        return wallet;
    }

    public List<TransactionResponse> getTransactions() {
        return transactions;
    }
}
