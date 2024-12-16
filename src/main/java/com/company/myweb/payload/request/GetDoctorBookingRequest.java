package com.company.myweb.payload.request;

import lombok.Data;

import java.util.Date;

@Data
public class GetDoctorBookingRequest {
    private Long doctorId;
    private Date bookingDate;
}
