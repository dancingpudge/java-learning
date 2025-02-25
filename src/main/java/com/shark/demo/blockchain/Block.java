package com.shark.demo.blockchain;


import java.util.List;

/**
 * @author: LiuH
 * @date: 2024/4/11 18:41
 */
public class Block {
    private int index; // 区块索引
    private String previousHash; // 前一区块的哈希值
    private List<Transaction> transactions; // 当前区块包含的交易列表
    private long timestamp; // 区块生成时间戳
    private String hash; // 当前区块的哈希值

    public Block(int index, String previousHash, List<Transaction> transactions, long timestamp) {
        this.index = index;
        this.previousHash = previousHash;
        this.transactions = transactions;
        this.timestamp = timestamp;

        // 实现简化版的SHA-256哈希计算，实际应用中应使用成熟的加密库如Bouncy Castle
        this.hash = calculateHash();
    }

    /**
     * 添加新的交易到当前区块
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        // 在实际应用中，每次添加交易后应重新计算区块哈希
        // 这里简化处理，仅演示区块链和交易的基本概念
    }

    /**
     * 计算当前区块的哈希值（简化版SHA-256）
     */
    private String calculateHash() {
        // 实际应用中应使用成熟的加密库如Bouncy Castle计算SHA-256哈希
        return "SimplifiedHash_" + index + "_" + previousHash + "_" + transactions + "_" + timestamp;
    }

    @Override
    public String toString() {
        return "Block{" +
                "index=" + index +
                ", previousHash='" + previousHash + '\'' +
                ", transactions=" + transactions +
                ", timestamp=" + timestamp +
                ", hash='" + hash + '\'' +
                '}';
    }
}



