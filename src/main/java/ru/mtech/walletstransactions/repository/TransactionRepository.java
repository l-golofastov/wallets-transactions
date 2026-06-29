package ru.mtech.walletstransactions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mtech.walletstransactions.entity.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByFromWalletIdOrToWalletIdOrderByCreatedAtDesc(Long fromWalletId, Long toWalletId);

    long countByCreatedAtGreaterThanEqualAndCreatedAtLessThan(LocalDateTime from, LocalDateTime to);
}
