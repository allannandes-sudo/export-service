package com.orders.export_service.service.impl;

import com.orders.export_service.entity.Order;
import com.orders.export_service.mapper.OrderMapper;
import com.orders.export_service.repository.ExportOrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompletedOrCanceledOrderServiceImplTest {

    @Mock
    private ExportOrderRepository exportOrderRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private CompletedOrCanceledOrderServiceImpl completedOrCanceledOrderServiceImpl;


    @Test
    void shouldSaveOrderEntityToRepositoryWhenValidOrderIsProcessed() {

        Order orderEntity = Order.builder().build();
        when(orderMapper.toEntity(any())).thenReturn(orderEntity);
        when(exportOrderRepository.save(orderEntity)).thenReturn(Mono.just(orderEntity));


        completedOrCanceledOrderServiceImpl.completedOrCanceledOrder(any());


        verify(exportOrderRepository).save(orderEntity);
    }
}