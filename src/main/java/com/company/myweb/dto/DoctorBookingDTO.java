package com.company.myweb.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class DoctorBookingDTO {
    private Long doctorId;
    private Date bookingDate;
    private List<BookingTimeInfoDTO> bookingTimeInfoDTOS = new ArrayList<>();
}
