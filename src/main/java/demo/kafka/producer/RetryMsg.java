package demo.kafka.producer;

public interface RetryMsg {
    /**
     * 分区算法
     *
     * @return
     */
    String getKey();

    /**
     * 退出充实策略
     *
     * @return
     */
    boolean shouldRetry();
}
