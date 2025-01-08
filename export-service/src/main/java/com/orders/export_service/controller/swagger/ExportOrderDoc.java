package com.orders.export_service.controller.swagger;

import com.orders.export_service.annotations.DefaultSwaggerMessage;
import com.orders.export_service.model.PagedOrderResponse;
import com.orders.export_service.model.enums.OrderStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Mono;

public interface ExportOrderDoc {


    @DefaultSwaggerMessage
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    Mono<PagedOrderResponse> getDeliveryOrders(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) OrderStatus status
    );
}