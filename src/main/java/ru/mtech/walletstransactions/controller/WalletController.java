package ru.mtech.walletstransactions.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mtech.walletstransactions.dto.WalletResponse;
import ru.mtech.walletstransactions.entity.Wallet;
import ru.mtech.walletstransactions.service.WalletService;

import java.util.List;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping
    public List<WalletResponse> getAllWallets() {
        return walletService.getAllWallets()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private WalletResponse toResponse(Wallet wallet) {
        return new WalletResponse(
                wallet.getId(),
                wallet.getAccountNumber(),
                wallet.getBalance(),
                wallet.getCurrency()
        );
    }
}
