package nlu.fit.bookinghotel.configurations;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaConfig {
    @Bean
    public CommonErrorHandler errorHandler(KafkaOperations<Object, Object> template) {
        return new DefaultErrorHandler(
                //Thành phần này áp dụng một chiến lược lùi lại cố định, với một khoảng trễ 1000 mili giây và tối đa 2 lần thử lại, trước khi thử lại xử lý tin nhắn.
                new DeadLetterPublishingRecoverer(template), new FixedBackOff(1000L, 2));
    }

    @Bean
    public NewTopic createTopic() {
        return new NewTopic("my-topic", 1, (short) 1);
    }
    @Bean
    public NewTopic createBooking() {
        return new NewTopic("booking", 1, (short) 1);
    }
}
