package com.orders.export_servive.repository;

import com.orders.export_servive.entity.Order;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ExportOrderRepository extends ReactiveMongoRepository<Order, String> {


}
