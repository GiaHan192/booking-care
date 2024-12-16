package com.company.myweb.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

@Data
public class CreateBookingTimeRequest {

    @NotNull
    private LocalTime fromTime;

    @NotNull
    private LocalTime toTime;
}
