package com.company.myweb.mapper;

import com.company.myweb.dto.BookingDTO;
import com.company.myweb.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "bookingTime", ignore = true)
    BookingDTO toDto(Booking booking);
}
