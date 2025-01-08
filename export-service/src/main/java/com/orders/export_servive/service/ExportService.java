package com.orders.export_servive.service;

import com.orders.export_servive.model.PagedOrderResponse;
import com.orders.export_servive.model.enums.OrderStatus;
import reactor.core.publisher.Mono;


public interface ExportService {

    Mono<PagedOrderResponse> getDeliveryOrders(int page, int size, String customerId, OrderStatus status);

}
