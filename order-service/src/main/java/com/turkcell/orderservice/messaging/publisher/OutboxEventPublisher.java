package com.turkcell.orderservice.messaging.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turkcell.orderservice.entity.OutboxMessage;
import com.turkcell.orderservice.entity.OutboxStatus;
import com.turkcell.orderservice.messaging.event.OrderCreatedEvent;
import com.turkcell.orderservice.repository.OutboxMessageRepository;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class OutboxEventPublisher {
   private final OutboxMessageRepository outboxMessageRepository;
   private final StreamBridge streamBridge;
   private final ObjectMapper objectMapper;

    public OutboxEventPublisher(OutboxMessageRepository outboxMessageRepository, StreamBridge streamBridge, ObjectMapper objectMapper) {
        this.outboxMessageRepository = outboxMessageRepository;
        this.streamBridge = streamBridge;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedRate = 5000)
   public void publishPendingEvents() throws JsonProcessingException {
       // Her x ms'de bir bu fonksiyonu yeni bir process olarak tetikle.
       // Polling -> 5snde bir değişsede değişmesede query çalışır.
       // CDC -> Change Data Capture -> Debezium
        System.out.println("Publishing pending events");
        List<OutboxMessage> pendingEvents = outboxMessageRepository.findByStatusOrderByCreatedAtAsc(OutboxStatus.PENDING);

        for (OutboxMessage pendingEvent : pendingEvents) {
            OrderCreatedEvent event = objectMapper.readValue(pendingEvent.getPayloadJson(), OrderCreatedEvent.class);

            Message<OrderCreatedEvent> msg = MessageBuilder
                    .withPayload(event)
                    .setHeader("EventID", pendingEvent.getEventId())
                    .build();
            boolean sent = streamBridge.send("orders", msg);

            if(!sent)
            {
                pendingEvent.setRetryCount(pendingEvent.getRetryCount() + 1);
                if(pendingEvent.getRetryCount() > 5){
                    pendingEvent.setStatus(OutboxStatus.FAILED);
                    pendingEvent.setProcessedAt(OffsetDateTime.now());
                }
            }else{
                pendingEvent.setStatus(OutboxStatus.SENT);
                pendingEvent.setProcessedAt(OffsetDateTime.now());
            }

            outboxMessageRepository.save(pendingEvent);

        }
   }
}
