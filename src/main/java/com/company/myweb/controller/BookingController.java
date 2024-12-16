package com.company.myweb.controller;

import com.company.myweb.dto.BookingDTO;
import com.company.myweb.dto.DoctorBookingDTO;
import com.company.myweb.entity.common.ApiResponse;
import com.company.myweb.payload.request.BookAppointmentRequest;
import com.company.myweb.service.interfaces.IBookingService;
import com.company.myweb.service.interfaces.IEmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final IBookingService bookingService;
    private final IEmailService emailService;

    public BookingController(IBookingService bookingServiceImp, IEmailService emailService) {
        this.bookingService = bookingServiceImp;
        this.emailService = emailService;
    }


    @PostMapping
    public ResponseEntity<ApiResponse<String>> booking(@RequestBody BookAppointmentRequest bookAppointmentRequest) {
        Boolean bookingResult = bookingService.bookAppointment(bookAppointmentRequest);
        if (bookingResult) {
            return ResponseEntity.ok(ApiResponse.success("Đặt lịch thành công"));
        } else {
            return ResponseEntity.badRequest().body(ApiResponse.failed("Đặt lịch thất bại"));
        }
    }

    @GetMapping("/doctor")
    public ResponseEntity<ApiResponse<DoctorBookingDTO>> getBookingOfDoctor(@RequestParam Long doctorId,
                                                                            @RequestParam Instant bookingDate) {
        return ResponseEntity.ok(ApiResponse.success(bookingService.getDoctorBooking(doctorId, Date.from(bookingDate))));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingDTO>> getBookingOfUser(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(bookingService.getBookingOfUser(userId));
    }

//    @PostMapping("/test-email")
//    public ResponseEntity<ApiResponse<Void>> testSendEmail() {
//        emailService.sendTextEmail("giahan@gmail.com", "Test Email", "Hello World");
//        return ResponseEntity.ok(ApiResponse.success());
//    }

}
