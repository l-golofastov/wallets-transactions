package ru.mtech.walletstransactions.exception;

public class NotEnoughMoneyException extends RuntimeException {

    public NotEnoughMoneyException() {
        super("Not enough money on wallet");
    }
}
