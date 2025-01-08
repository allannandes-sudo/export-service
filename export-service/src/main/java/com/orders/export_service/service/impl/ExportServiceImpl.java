package com.orders.export_service.service.impl;

import com.orders.export_service.entity.Order;
import com.orders.export_service.mapper.OrderMapper;
import com.orders.export_service.model.PagedOrderResponse;
import com.orders.export_service.model.enums.OrderStatus;
import com.orders.export_service.repository.ExportOrderRepository;
import com.orders.export_service.service.ExportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExportServiceImpl implements ExportService {

    private final ExportOrderRepository exportOrderRepository;
    private final OrderMapper orderMapper;

    public Mono<PagedOrderResponse> getDeliveryOrders(int page, int size, String customerId, OrderStatus status) {

        int adjustedPage = (page == 0) ? 1 : page;

        return exportOrderRepository.findAll(Sort.by("createDateTime").ascending())
                .filter(order -> customerId == null || order.getCustomerId().equals(customerId))
                .filter(order -> status == null || order.getStatus().equals(status))
                .collectList()
                .map(filteredOrders -> {
                    int totalSize = filteredOrders.size();
                    log.info("Total size: {}", totalSize);
                    log.info("page: {}, size: {}", page, size);

                    int adjustedPageIndex = page > 0 ? page - 1 : 0;
                    int start = adjustedPageIndex * size;
                    int end = Math.min(start + size, totalSize);

                    List<Order> pagedOrders = filteredOrders.subList(start, end);

                    return orderMapper.toPagedOrderResponse(pagedOrders, adjustedPage, size, totalSize);

                })
                .doOnNext(order -> log.info("Order fetched: {}", order))
                .doOnError(error -> log.error("Error fetching orders: {}", error.getMessage(), error));
    }
}
