package com.orders.export_service.service;

import com.orders.export_service.model.PagedOrderResponse;
import com.orders.export_service.model.enums.OrderStatus;
import reactor.core.publisher.Mono;


public interface ExportService {

    Mono<PagedOrderResponse> getDeliveryOrders(int page, int size, String customerId, OrderStatus status);

}
