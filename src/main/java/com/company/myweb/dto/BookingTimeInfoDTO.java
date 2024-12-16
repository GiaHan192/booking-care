package com.company.myweb.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
public class BookingTimeInfoDTO {
    private Long id;
    private LocalTime fromTime;
    private LocalTime toTime;
    private BigDecimal price;
    private Boolean booked = false;
}
