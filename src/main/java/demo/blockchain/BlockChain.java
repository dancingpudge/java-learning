package demo.blockchain;


import java.util.ArrayList;
import java.util.List;

/**
 * @author: LiuH
 * @date: 2024/4/11 18:41
 */
public class BlockChain {
    private List<Block> blocks; // 存储所有区块的列表

    public BlockChain() {
        this.blocks = new ArrayList<>();
        // 创世区块
        Block genesisBlock = new Block(0, "0", new ArrayList<>(), System.currentTimeMillis());
        blocks.add(genesisBlock);
    }

    /**
     * 添加新的交易到待打包区块的交易池
     */
    public void addTransaction(String sender, String receiver, int amount) {
        // 在实际应用中，这里应包含更复杂的交易验证逻辑
        Transaction transaction = new Transaction(sender, receiver, amount);
        blocks.get(blocks.size() - 1).addTransaction(transaction);
    }

    /**
     * 获取当前区块链中的所有区块
     */
    public List<Block> getBlocks() {
        return blocks;
    }
}