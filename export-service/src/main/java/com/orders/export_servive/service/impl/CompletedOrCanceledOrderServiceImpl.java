package com.orders.export_servive.service.impl;


import com.orders.export_servive.mapper.OrderMapper;
import com.orders.export_servive.payload.inbound.ProcessingOrder;
import com.orders.export_servive.repository.ExportOrderRepository;
import com.orders.export_servive.service.CompletedOrCanceledOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompletedOrCanceledOrderServiceImpl implements CompletedOrCanceledOrderService {

    private final ExportOrderRepository exportOrderRepository;
    private final OrderMapper orderMapper;

    @Override
    public void completedOrCanceledOrder(ProcessingOrder processingOrder) {
        log.info("Processando pedido: {}", processingOrder);
        exportOrderRepository.save(orderMapper.toEntity(processingOrder))
                .doOnSuccess(savedOrder -> log.info("Pedido salvo com sucesso: {}", savedOrder))
                .doOnError(error -> log.error("Erro ao salvar pedido: {}", error.getMessage(), error))
                .subscribe();

    }
}
