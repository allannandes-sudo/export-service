package com.orders.export_servive.service;

import com.orders.export_servive.payload.inbound.ProcessingOrder;

public interface CompletedOrCanceledOrderService {
    void completedOrCanceledOrder(ProcessingOrder processingOrder);
}
