package com.company.myweb.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingPriceDTO {
    private Long id;
    private BigDecimal price;
    private DoctorDTO doctor;
    private BookingTimeDTO bookingTime;
}
