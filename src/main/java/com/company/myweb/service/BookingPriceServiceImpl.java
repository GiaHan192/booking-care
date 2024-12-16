package com.company.myweb.service;

import com.company.myweb.dto.BookingPriceDTO;
import com.company.myweb.entity.BookingPrice;
import com.company.myweb.entity.BookingTime;
import com.company.myweb.entity.Doctor;
import com.company.myweb.entity.common.ApiException;
import com.company.myweb.mapper.BookingTimeMapper;
import com.company.myweb.mapper.DoctorMapper;
import com.company.myweb.payload.request.CreateBookingPriceRequest;
import com.company.myweb.repository.BookingPriceRepository;
import com.company.myweb.repository.BookingTimeRepository;
import com.company.myweb.repository.DoctorRepository;
import com.company.myweb.service.interfaces.IBookingPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingPriceServiceImpl implements IBookingPriceService {

    private final BookingTimeRepository bookingTimeRepository;
    private final BookingPriceRepository bookingPriceRepository;
    private final DoctorRepository doctorRepository;
    private final BookingTimeMapper bookingTimeMapper;
    private final DoctorMapper doctorMapper;

    @Override
    public BookingPriceDTO save(CreateBookingPriceRequest request) {
        BookingTime bookingTime = bookingTimeRepository.findById(request.getBookingTimeId())
                .orElseThrow(() -> ApiException.create(HttpStatus.BAD_REQUEST).withMessage("Booking time không tồn tại"));

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> ApiException.create(HttpStatus.BAD_REQUEST).withMessage("Doctor không tồn tại"));
        BookingPrice bookingPrice = BookingPrice.builder()
                .price(request.getPrice())
                .doctor(doctor)
                .bookingTime(bookingTime)
                .build();
        bookingPriceRepository.save(bookingPrice);
        return BookingPriceDTO.builder()
                .doctor(doctorMapper.toDto(doctor))
                .bookingTime(bookingTimeMapper.toDto(bookingTime))
                .id(bookingPrice.getId())
                .price(bookingPrice.getPrice())
                .build();
    }

    @Override
    public BookingPriceDTO update(Long id, CreateBookingPriceRequest request) {
        BookingTime bookingTime = bookingTimeRepository.findById(request.getBookingTimeId())
                .orElseThrow(() -> ApiException.create(HttpStatus.BAD_REQUEST).withMessage("Booking time không tồn tại"));

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> ApiException.create(HttpStatus.BAD_REQUEST).withMessage("Doctor không tồn tại"));

        BookingPrice bookingPrice = bookingPriceRepository.findById(id)
                .orElseThrow(() -> ApiException.create(HttpStatus.BAD_REQUEST).withMessage("Booking price không tồn tại"));
        bookingPrice.setPrice(request.getPrice());
        bookingPrice.setDoctor(doctor);
        bookingPrice.setBookingTime(bookingTime);
        bookingPriceRepository.save(bookingPrice);

        return BookingPriceDTO.builder()
                .doctor(doctorMapper.toDto(doctor))
                .bookingTime(bookingTimeMapper.toDto(bookingTime))
                .id(bookingPrice.getId())
                .price(bookingPrice.getPrice())
                .build();
    }

    private BookingPriceDTO convert(BookingPrice bookingPrice){
        return BookingPriceDTO.builder()
                .doctor(doctorMapper.toDto(bookingPrice.getDoctor()))
                .bookingTime(bookingTimeMapper.toDto(bookingPrice.getBookingTime()))
                .id(bookingPrice.getId())
                .price(bookingPrice.getPrice())
                .build();
    }

    @Override
    public List<BookingPriceDTO> findAll() {
        return bookingPriceRepository.findAll()
                .stream()
                .map(this::convert)
                .toList();
    }

    @Override
    public void delete(Long id) {
        bookingPriceRepository.deleteById(id);
    }
}
