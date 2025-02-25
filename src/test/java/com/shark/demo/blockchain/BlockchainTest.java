package com.shark.demo.blockchain;

/**
 * @author: LiuH
 * @date: 2024/4/11 18:48
 */
public class BlockchainTest {
    public static void main(String[] args) {
        // 创建一个新的区块链实例
        BlockChain blockchain = new BlockChain();

        // 向区块链中添加一些交易数据
        blockchain.addTransaction("Alice", "Bob", 10);
        blockchain.addTransaction("Bob", "Charlie", 5);
        blockchain.addTransaction("Charlie", "Alice", 5);

        // 打印出当前区块链的状态
        System.out.println("Current Blockchain:");
        for (Block block : blockchain.getBlocks()) {
            System.out.println(block);
        }
    }

}
