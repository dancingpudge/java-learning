package demo.kafka.producer;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
public class MessagePusher {

    private static final String TOPIC = "从配置中获取";
    private static final String QUEUE_NAME = "从配置中获取";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final RBlockingDeque rBlockingDeque;

    @Autowired
    MessagePusher(KafkaTemplate<String, String> kafkaTemplate, RedissonClient redissonClient) {
        this.kafkaTemplate = kafkaTemplate;
        this.rBlockingDeque = redissonClient.getBlockingDeque(QUEUE_NAME);
    }

    /**
     * 业务数据发送
     */
    public <M extends RetryMsg> void push(M detail) {

        String msg = JSONObject.toJSONStringWithDateFormat(detail, "yyyy-MM-dd HH:mm:ss");

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(TOPIC, detail.getKey(), msg);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("sendMessage success  detail:{}", msg);
            }

            @Override
            public void onFailure(Throwable ex) {
                log.info("sendMessage fail and offer to BlockingQueue:{} detail:{}", rBlockingDeque.offer(detail), msg);
            }
        });
    }


    @Scheduled(fixedDelay = 60000, initialDelay = 60000)
    private <M extends RetryMsg> void retry() {
        log.info("sendMessage retry----------------");
        M detail = (M) rBlockingDeque.poll();
        if (detail != null && detail.shouldRetry()) {
            push(detail);
        }
    }
}
