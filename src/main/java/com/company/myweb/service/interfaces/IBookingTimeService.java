package com.company.myweb.service.interfaces;

import com.company.myweb.entity.BookingTime;
import com.company.myweb.payload.request.CreateBookingTimeRequest;

import java.util.List;

public interface IBookingTimeService {

    BookingTime save(CreateBookingTimeRequest bookingTime);

    BookingTime update(Long id, CreateBookingTimeRequest bookingTime);

    List<BookingTime> findAll();

    void delete(Long id);
}
