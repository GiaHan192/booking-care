package com.company.myweb.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateBookingPriceRequest {
    @NotNull
    private Long doctorId;

    @NotNull
    private Long bookingTimeId;

    @NotNull
    private BigDecimal price;
}
