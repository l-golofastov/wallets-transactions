package ru.mtech.walletstransactions.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mtech.walletstransactions.entity.Wallet;
import ru.mtech.walletstransactions.repository.WalletRepository;

import java.util.List;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Transactional(readOnly = true)
    public List<Wallet> getAllWallets() {
        return walletRepository.findAll();
    }
}
