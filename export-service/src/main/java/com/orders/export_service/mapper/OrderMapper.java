package com.orders.export_service.mapper;

import com.orders.export_service.entity.Order;
import com.orders.export_service.model.PagedOrderResponse;
import com.orders.export_service.payload.inbound.ProcessingOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    Order toEntity(ProcessingOrder processingOrder);

    @Mapping(target = "page", source = "page")
    @Mapping(target = "size", source = "size")
    @Mapping(target = "totalSize", source = "totalSize")
    @Mapping(target = "orders",  source = "orders")
    PagedOrderResponse toPagedOrderResponse(List<Order> orders, int page, int size, int totalSize);

}
