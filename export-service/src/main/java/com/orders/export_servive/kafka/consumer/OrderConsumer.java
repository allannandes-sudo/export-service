package com.orders.export_servive.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orders.export_servive.exception.MessageProcessingException;
import com.orders.export_servive.payload.inbound.ProcessingOrder;
import com.orders.export_servive.service.CompletedOrCanceledOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderConsumer {

    private final ObjectMapper objectMapper;
    private final CompletedOrCanceledOrderService completedOrCanceledOrderService;


    @KafkaListener(
            topics = "${topic.completed-event:null}",
            groupId = "${spring.kafka.consumer.group-id}",
            clientIdPrefix = "order"
    )
    @Transactional
    public void orderCompleted(@Payload String message) {
        log.info("Pedido recebido: {}", message);
        try {
            var dto = objectMapper.readValue(message, ProcessingOrder.class);
            completedOrCanceledOrderService.completedOrCanceledOrder(dto);
        } catch (JsonProcessingException e) {
            log.error("Erro ao processar mensagem JSON: {}", message, e);
            throw new MessageProcessingException("Erro ao processar mensagem JSON", e);
        } catch (Exception e) {
            log.error("Erro ao processar pedido: {}", e.getMessage(), e);
            throw new MessageProcessingException("Erro ao processar pedido", e);
        }
    }

}
