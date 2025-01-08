package com.orders.export_service.service;

import com.orders.export_service.payload.inbound.ProcessingOrder;

public interface CompletedOrCanceledOrderService {
    void completedOrCanceledOrder(ProcessingOrder processingOrder);
}
