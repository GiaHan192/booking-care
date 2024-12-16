package com.company.myweb.service;

import com.company.myweb.entity.BookingTime;
import com.company.myweb.entity.common.ApiException;
import com.company.myweb.mapper.BookingTimeMapper;
import com.company.myweb.payload.request.CreateBookingTimeRequest;
import com.company.myweb.repository.BookingTimeRepository;
import com.company.myweb.service.interfaces.IBookingTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingTimeServiceImpl implements IBookingTimeService {
    private final BookingTimeRepository bookingTimeRepository;
    private final BookingTimeMapper bookingTimeMapper;

    @Override
    public BookingTime save(CreateBookingTimeRequest bookingTime) {
        BookingTime entity = bookingTimeMapper.toEntity(bookingTime);
        return bookingTimeRepository.save(entity);
    }

    @Override
    public BookingTime update(Long id, CreateBookingTimeRequest bookingTimeRequest) {
        BookingTime bookingTime = bookingTimeRepository.findById(id)
                .orElseThrow(() -> ApiException.create(HttpStatus.BAD_REQUEST).withMessage("Không tìm thấy bản ghi:" + id));
        bookingTime.setFromTime(bookingTimeRequest.getFromTime());
        bookingTime.setToTime(bookingTimeRequest.getToTime());
        return bookingTimeRepository.save(bookingTime);
    }

    @Override
    public List<BookingTime> findAll() {
        return bookingTimeRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        bookingTimeRepository.deleteById(id);
    }
}
