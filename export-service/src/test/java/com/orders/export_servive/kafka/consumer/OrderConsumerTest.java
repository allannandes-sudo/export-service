package com.orders.export_servive.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orders.export_servive.model.Product;
import com.orders.export_servive.model.enums.OrderStatus;
import com.orders.export_servive.payload.inbound.ProcessingOrder;
import com.orders.export_servive.service.CompletedOrCanceledOrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class OrderConsumerTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private CompletedOrCanceledOrderService completedOrCanceledOrderService;

    @InjectMocks
    private OrderConsumer orderConsumer;

    @Test
    void shouldSuccessfullyProcessValidJsonMessageAndCallCompletedOrCanceledOrder() throws Exception {

        String validJsonMessage = "{\"orderId\": \"123\", \"customerId\": \"456\", \"orderDate\": \"2023-10-01T10:00:00\", \"deliveryDate\": \"2023-10-05T10:00:00\", \"completionDate\": \"2023-10-06T10:00:00\", \"products\": [{\"productId\": \"789\", \"quantity\": 1}], \"status\": \"COMPLETED\"}";
        Product product = new Product();
        product.setProductId("789");
        product.setQuantity(1);
        ProcessingOrder processingOrder = new ProcessingOrder("123", "456", LocalDateTime.parse("2023-10-01T10:00:00"), LocalDateTime.parse("2023-10-05T10:00:00"), LocalDateTime.parse("2023-10-06T10:00:00"), List.of(product), OrderStatus.COMPLETED, null, null); // Add the missing arguments

        when(objectMapper.readValue(validJsonMessage, ProcessingOrder.class)).thenReturn(processingOrder);


        orderConsumer.orderCompleted(validJsonMessage);


        verify(completedOrCanceledOrderService, times(1)).completedOrCanceledOrder(processingOrder);
    }
}