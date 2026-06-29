package ru.mtech.walletstransactions.dto;

import java.math.BigDecimal;

public class SummaryReportResponse {

    private BigDecimal totalAmount;
    private long successfulTransactionsToday;

    public SummaryReportResponse(BigDecimal totalAmount, long successfulTransactionsToday) {
        this.totalAmount = totalAmount;
        this.successfulTransactionsToday = successfulTransactionsToday;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public long getSuccessfulTransactionsToday() {
        return successfulTransactionsToday;
    }
}
