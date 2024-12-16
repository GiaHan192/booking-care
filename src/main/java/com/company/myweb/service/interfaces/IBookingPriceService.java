package com.company.myweb.service.interfaces;

import com.company.myweb.dto.BookingPriceDTO;
import com.company.myweb.entity.BookingPrice;
import com.company.myweb.entity.BookingTime;
import com.company.myweb.payload.request.CreateBookingPriceRequest;
import com.company.myweb.payload.request.CreateBookingTimeRequest;

import java.util.List;

public interface IBookingPriceService {

    BookingPriceDTO save(CreateBookingPriceRequest createBookingPriceRequest);

    BookingPriceDTO update(Long id, CreateBookingPriceRequest createBookingPriceRequest);

    List<BookingPriceDTO> findAll();

    void delete(Long id);
}
