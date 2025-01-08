package com.orders.export_service.controller;

import com.orders.export_service.controller.swagger.ExportOrderDoc;
import com.orders.export_service.model.PagedOrderResponse;
import com.orders.export_service.model.enums.OrderStatus;
import com.orders.export_service.service.ExportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Export Order Service", description = "Método para expor os pedidos")
@RequestMapping(path = "/orders")
public class ExportOrderRest implements ExportOrderDoc {

    private final ExportService exportService;

    @GetMapping
    public Mono<PagedOrderResponse> getDeliveryOrders(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) OrderStatus status
    ) {
        return exportService.getDeliveryOrders(page, size, customerId, status)
                .doOnNext(order -> log.info("Pedido encontrado: {}", order))
                .doOnError(throwable -> log.info("Erro ao buscar pedidos: {}", throwable.getMessage()));
    }
}
