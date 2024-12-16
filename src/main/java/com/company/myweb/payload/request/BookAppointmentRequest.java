package com.company.myweb.payload.request;

import com.company.myweb.entity.User;
import lombok.Data;

import java.time.Instant;
import java.util.Date;

@Data
public class BookAppointmentRequest {
    private Long doctorId;
    private Long bookingTimeId;
    private Date bookingDate;
    private String patientName;
    private String phoneNumber;
    private String email;
    private User.GENDER gender;
    private String address;
    private String reason;
    private Boolean bookingType = true; // true for USER - false for OTHER
}
