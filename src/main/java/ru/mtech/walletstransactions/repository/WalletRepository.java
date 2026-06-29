package ru.mtech.walletstransactions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mtech.walletstransactions.entity.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
