package com.orders.export_servive.controller;


import com.orders.export_servive.entity.Order;
import com.orders.export_servive.model.PagedOrderResponse;
import com.orders.export_servive.model.enums.OrderStatus;
import com.orders.export_servive.service.ExportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ExportOrderRestTest {
    @Test
    void shouldReturnPagedOrderResponseWithDefaultPaginationWhenNoParametersProvided() {

        ExportService exportService = mock(ExportService.class);
        ExportOrderRest exportOrderRest = new ExportOrderRest(exportService);
        PagedOrderResponse expectedResponse = PagedOrderResponse.builder()
                .page(1)
                .size(10)
                .totalSize(0)
                .orders(null)
                .build();
        Mockito.when(exportService.getDeliveryOrders(1, 10, null, null))
                .thenReturn(Mono.just(expectedResponse));


        Mono<PagedOrderResponse> responseMono = exportOrderRest.getDeliveryOrders(1, 10, null, null);


        StepVerifier.create(responseMono)
                .expectNext(expectedResponse)
                .verifyComplete();
    }

    @Test
    void shouldReturnPagedOrderResponseFilteredByCustomerIdWhenCustomerIdProvided() {

        ExportService exportService = mock(ExportService.class);
        ExportOrderRest exportOrderRest = new ExportOrderRest(exportService);
        String customerId = "customer123";
        Order mockOrder = mock(Order.class);
        PagedOrderResponse expectedResponse = PagedOrderResponse.builder()
                .page(1)
                .size(10)
                .totalSize(1)
                .orders(Collections.singletonList(mockOrder))
                .build();
        Mockito.when(exportService.getDeliveryOrders(1, 10, customerId, null))
                .thenReturn(Mono.just(expectedResponse));


        Mono<PagedOrderResponse> responseMono = exportOrderRest.getDeliveryOrders(1, 10, customerId, null);


        StepVerifier.create(responseMono)
                .expectNext(expectedResponse)
                .verifyComplete();
    }

    @Test
    void shouldReturnPagedOrderResponseFilteredByOrderStatusWhenOrderStatusProvided() {

        ExportService exportService = mock(ExportService.class);
        ExportOrderRest exportOrderRest = new ExportOrderRest(exportService);
        OrderStatus status = OrderStatus.COMPLETED;
        Order mockOrder = mock(Order.class);
        PagedOrderResponse expectedResponse = PagedOrderResponse.builder()
                .page(1)
                .size(10)
                .totalSize(1)
                .orders(Collections.singletonList(mockOrder))
                .build();
        Mockito.when(exportService.getDeliveryOrders(1, 10, null, status))
                .thenReturn(Mono.just(expectedResponse));


        Mono<PagedOrderResponse> responseMono = exportOrderRest.getDeliveryOrders(1, 10, null, status);


        StepVerifier.create(responseMono)
                .expectNext(expectedResponse)
                .verifyComplete();
    }

    @Test
    void shouldHandleErrorFromExportServiceAndLogErrorMessage() {

        ExportService exportService = mock(ExportService.class);
        ExportOrderRest exportOrderRest = new ExportOrderRest(exportService);
        String errorMessage = "Service error";
        Mockito.when(exportService.getDeliveryOrders(1, 10, null, null))
                .thenReturn(Mono.error(new RuntimeException(errorMessage)));


        Mono<PagedOrderResponse> responseMono = exportOrderRest.getDeliveryOrders(1, 10, null, null);


        StepVerifier.create(responseMono)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && throwable.getMessage().equals(errorMessage))
                .verify();
    }
}