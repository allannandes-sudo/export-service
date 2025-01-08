package com.orders.export_service.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    private final String displayName;
}
