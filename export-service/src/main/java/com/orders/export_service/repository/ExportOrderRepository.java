package com.orders.export_service.repository;

import com.orders.export_service.entity.Order;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ExportOrderRepository extends ReactiveMongoRepository<Order, String> {


}
