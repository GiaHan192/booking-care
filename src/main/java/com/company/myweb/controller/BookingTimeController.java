package com.company.myweb.controller;

import com.company.myweb.entity.BookingTime;
import com.company.myweb.payload.request.CreateBookingTimeRequest;
import com.company.myweb.service.interfaces.IBookingTimeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/booking-time")
@RequiredArgsConstructor
public class BookingTimeController {

    private final IBookingTimeService bookingTimeService;

    @PostMapping
    public ResponseEntity<BookingTime> create(@Valid @RequestBody CreateBookingTimeRequest bookingTime) {
        log.info("(create) bookingTime: {}", bookingTime);
        return ResponseEntity.ok(bookingTimeService.save(bookingTime));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingTime> update(@PathVariable Long id, @Valid @RequestBody CreateBookingTimeRequest bookingTime) {
        log.info("(update) id: {}, bookingTime: {}", id, bookingTime);
        return ResponseEntity.ok(bookingTimeService.update(id, bookingTime));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("(delete) id: {}", id);
        bookingTimeService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<BookingTime>> findAll() {
        log.info("(findAll) call");
        return ResponseEntity.ok(bookingTimeService.findAll());
    }

}
