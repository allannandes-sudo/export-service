package com.orders.export_servive.service.impl;

import com.orders.export_servive.entity.Order;
import com.orders.export_servive.mapper.OrderMapper;
import com.orders.export_servive.repository.ExportOrderRepository;
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