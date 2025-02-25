package com.shark.demo.blockchain;

/**
 * @author: LiuH
 * @date: 2024/4/11 18:46
 */
public class Transaction {
    private String sender;
    private String receiver;
    private int amount;

    public Transaction(String sender, String receiver, int amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", amount=" + amount +
                '}';
    }
}