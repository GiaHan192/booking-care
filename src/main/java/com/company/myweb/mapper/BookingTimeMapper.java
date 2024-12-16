package com.company.myweb.mapper;

import com.company.myweb.dto.BookingTimeDTO;
import com.company.myweb.entity.BookingTime;
import com.company.myweb.payload.request.CreateBookingTimeRequest;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface BookingTimeMapper {

    BookingTime toEntity(CreateBookingTimeRequest bookingTimeRequest);

    BookingTimeDTO toDto(BookingTime bookingTime);
}
