package com.orders.export_service.service.impl;

import com.orders.export_service.entity.Order;
import com.orders.export_service.mapper.OrderMapper;
import com.orders.export_service.model.PagedOrderResponse;
import com.orders.export_service.model.enums.OrderStatus;
import com.orders.export_service.repository.ExportOrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ExportServiceImplTest {

    @Mock
    private ExportOrderRepository exportOrderRepository;
    @Mock
    private OrderMapper orderMapper;
    @InjectMocks
    private ExportServiceImpl exportServiceImpl;

    @Test
    void shouldHandlePageSizeLargerThanTotalOrders() {

        int page = 1;
        int size = 10;
        String customerId = null;
        OrderStatus status = null;

        Order order1 = mock(Order.class);
        Order order2 = mock(Order.class);

        List<Order> allOrders = List.of(order1, order2);

        when(exportOrderRepository.findAll(Sort.by("createDateTime").ascending()))
                .thenReturn(Flux.fromIterable(allOrders));


        when(orderMapper.toPagedOrderResponse(eq(allOrders), eq(page), eq(size), eq(allOrders.size())))
                .thenReturn(new PagedOrderResponse(page, size, allOrders.size(), allOrders));


        Mono<PagedOrderResponse> result = exportServiceImpl.getDeliveryOrders(page, size, customerId, status);


        StepVerifier.create(result)
                .expectNextMatches(response -> response.getOrders().equals(allOrders) && response.getOrders().size() == allOrders.size())
                .verifyComplete();
    }

    @Test
    void shouldReturnAllOrdersWhenCustomerIdAndStatusAreNull() {

        int page = 1;
        int size = 10;
        String customerId = null;
        OrderStatus status = null;
        Order order1 = mock(Order.class);
        Order order2 = mock(Order.class);
        Order order3 = mock(Order.class);
        List<Order> allOrders = List.of(order1, order2, order3);

        when(exportOrderRepository.findAll(Sort.by("createDateTime").ascending()))
                .thenReturn(Flux.fromIterable(allOrders));

        when(orderMapper.toPagedOrderResponse(anyList(), eq(page), eq(size), eq(allOrders.size())))
                .thenReturn(new PagedOrderResponse(page, size, allOrders.size(), allOrders));


        Mono<PagedOrderResponse> result = exportServiceImpl.getDeliveryOrders(page, size, customerId, status);


        StepVerifier.create(result)
                .expectNextMatches(response -> response.getOrders().size() == allOrders.size())
                .verifyComplete();
    }

    @Test
    void shouldAdjustPageNumberToOneWhenZeroIsProvided() {

        int page = 0;
        int size = 2;
        String customerId = null;
        OrderStatus status = null;
        Order order1 = mock(Order.class);
        Order order2 = mock(Order.class);
        Order order3 = mock(Order.class);
        List<Order> allOrders = List.of(order1, order2, order3);

        when(exportOrderRepository.findAll(Sort.by("createDateTime").ascending()))
                .thenReturn(Flux.fromIterable(allOrders));


        List<Order> expectedPagedOrders = List.of(order1, order2);

        when(orderMapper.toPagedOrderResponse(eq(expectedPagedOrders), eq(1), eq(size), eq(allOrders.size())))
                .thenReturn(new PagedOrderResponse(1, size, allOrders.size(), expectedPagedOrders));


        Mono<PagedOrderResponse> result = exportServiceImpl.getDeliveryOrders(page, size, customerId, status);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getOrders().equals(expectedPagedOrders))
                .verifyComplete();
    }
}