package com.company.myweb.controller;

import com.company.myweb.dto.BookingPriceDTO;
import com.company.myweb.entity.BookingPrice;
import com.company.myweb.payload.request.CreateBookingPriceRequest;
import com.company.myweb.service.interfaces.IBookingPriceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/booking-price")
@RequiredArgsConstructor
public class BookingPriceController {

    private final IBookingPriceService bookingPriceService;

    @PostMapping
    public ResponseEntity<BookingPriceDTO> create(@Valid @RequestBody CreateBookingPriceRequest request) {
        log.info("(create) request: {}", request);
        return ResponseEntity.ok(bookingPriceService.save(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingPriceDTO> update(@PathVariable Long id, @Valid @RequestBody CreateBookingPriceRequest request) {
        log.info("(update) id: {}, request: {}", id, request);
        return ResponseEntity.ok(bookingPriceService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("(delete) id: {}", id);
        bookingPriceService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<BookingPriceDTO>> findAll() {
        log.info("(findAll) call");
        return ResponseEntity.ok(bookingPriceService.findAll());
    }

}
