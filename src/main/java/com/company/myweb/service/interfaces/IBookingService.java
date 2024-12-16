package com.company.myweb.service.interfaces;

import com.company.myweb.dto.BookingDTO;
import com.company.myweb.dto.DoctorBookingDTO;
import com.company.myweb.entity.Booking;
import com.company.myweb.payload.request.BookAppointmentRequest;

import java.util.Date;
import java.util.List;

public interface IBookingService {
    Boolean bookAppointment(BookAppointmentRequest appointmentRequest);

    DoctorBookingDTO getDoctorBooking(Long doctorId, Date bookingDate);

    List<BookingDTO> getBookingOfUser(Integer userId);
}
