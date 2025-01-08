package com.orders.export_servive.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.orders.export_servive.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PagedOrderResponse {

    private int page;
    private int size;
    private int totalSize;
    private List<Order> orders;
}