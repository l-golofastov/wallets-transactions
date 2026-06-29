package ru.mtech.walletstransactions.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mtech.walletstransactions.dto.CreateTransactionRequest;
import ru.mtech.walletstransactions.entity.Transaction;
import ru.mtech.walletstransactions.entity.Wallet;
import ru.mtech.walletstransactions.exception.NotEnoughMoneyException;
import ru.mtech.walletstransactions.repository.TransactionRepository;
import ru.mtech.walletstransactions.repository.WalletRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void shouldRejectPaymentWhenSenderHasNotEnoughMoney() {
        Wallet fromWallet = new Wallet("ACC-001", new BigDecimal("100.00"), "RUB");
        fromWallet.setId(1L);

        Wallet toWallet = new Wallet("ACC-002", new BigDecimal("50.00"), "RUB");
        toWallet.setId(2L);

        CreateTransactionRequest request = new CreateTransactionRequest(
                1L,
                2L,
                new BigDecimal("150.00")
        );

        when(walletRepository.findById(1L)).thenReturn(Optional.of(fromWallet));
        when(walletRepository.findById(2L)).thenReturn(Optional.of(toWallet));

        assertThrows(NotEnoughMoneyException.class, () -> transactionService.createTransaction(request));

        assertEquals(new BigDecimal("100.00"), fromWallet.getBalance());
        assertEquals(new BigDecimal("50.00"), toWallet.getBalance());

        verify(walletRepository, never()).save(any(Wallet.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }
}
