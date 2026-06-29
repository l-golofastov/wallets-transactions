package ru.mtech.walletstransactions.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mtech.walletstransactions.dto.BalanceReportResponse;
import ru.mtech.walletstransactions.dto.SummaryReportResponse;
import ru.mtech.walletstransactions.service.ReportService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/balance/{walletId}")
    public BalanceReportResponse getBalanceReport(@PathVariable Long walletId) {
        return reportService.getBalanceReport(walletId);
    }

    @GetMapping("/summary")
    public SummaryReportResponse getSummaryReport() {
        return reportService.getSummaryReport();
    }
}
