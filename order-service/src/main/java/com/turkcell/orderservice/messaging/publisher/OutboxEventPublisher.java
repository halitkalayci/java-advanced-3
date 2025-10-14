package com.turkcell.orderservice.messaging.publisher;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class OutboxEventPublisher {

   @Scheduled(fixedRate = 5000)
   public void publishPendingEvents() {
       // Her x ms'de bir bu fonksiyonu yeni bir process olarak tetikle.
       // Polling -> 5snde bir değişsede değişmesede query çalışır.
       // CDC -> Change Data Capture
   }
}
