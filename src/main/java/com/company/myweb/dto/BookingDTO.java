package com.company.myweb.dto;

import com.company.myweb.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class BookingDTO {
    private Long id;
    private DoctorDTO doctor;
    private BookingTimeDTO bookingTime;
    private Date bookingDate;
    private String address;
    private String reason;
    private String patientName;
    private String phoneNumber;
    private String email;
    private User.GENDER gender;
}
