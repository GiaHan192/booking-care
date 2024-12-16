package com.company.myweb.service;

import com.company.myweb.dto.BookingDTO;
import com.company.myweb.dto.BookingTimeInfoDTO;
import com.company.myweb.dto.DoctorBookingDTO;
import com.company.myweb.entity.*;
import com.company.myweb.entity.common.ApiException;
import com.company.myweb.mapper.BookingMapper;
import com.company.myweb.mapper.BookingTimeMapper;
import com.company.myweb.mapper.DoctorMapper;
import com.company.myweb.payload.request.BookAppointmentRequest;
import com.company.myweb.repository.BookingRepository;
import com.company.myweb.repository.BookingTimeRepository;
import com.company.myweb.repository.DoctorRepository;
import com.company.myweb.repository.UserRepository;
import com.company.myweb.service.interfaces.IBookingService;
import com.company.myweb.service.interfaces.IEmailService;
import com.company.myweb.utils.IOUtils;
import com.company.myweb.utils.SecurityUtil;
import com.company.myweb.utils.TimeIgnoringComparator;
import com.company.myweb.utils.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService {

    private static final Logger log = LoggerFactory.getLogger(BookingService.class);
    private final String BOOKING_CONFIRMATION_TEMPLATE = "static/template/booking-confirmation.html";

    private final BookingRepository bookingRepository;
    private final DoctorRepository doctorRepository;
    private final TimeIgnoringComparator ignoringComparator;
    private final BookingTimeRepository bookingTimeRepository;
    private final UserRepository userRepository;
    private final IEmailService emailService;
    private final BookingMapper bookingMapper;
    private final BookingTimeMapper bookingTimeMapper;
    private final DoctorMapper doctorMapper;


    @Override
    public Boolean bookAppointment(BookAppointmentRequest request) {


        Long doctorId = request.getDoctorId();
        Doctor doctor = doctorRepository
                .findById(doctorId).orElseThrow(() -> ApiException.create(HttpStatus.BAD_REQUEST)
                        .withMessage("Không tìm thấy bác sĩ với id:" + doctorId));

        BookingTime bookingTime = bookingTimeRepository.findById(request.getBookingTimeId())
                .orElseThrow(() -> ApiException.create(HttpStatus.BAD_REQUEST)
                        .withMessage("Không tìm lịch khám:" + request.getBookingTimeId()));

        bookingRepository.countByBookingDateBetweenAndDoctorAndBookingTime(
                TimeUtil.getStartTimeOfDate(request.getBookingDate()),
                TimeUtil.getEndTimeOfDate(request.getBookingDate()),
                doctor,
                bookingTime
        ).ifPresent(count -> {
            if (count > 0) {
                throw ApiException.create(HttpStatus.BAD_REQUEST)
                        .withMessage("Giời khám đã được đặt, vui lòng chọn giờ khác");
            }
        });


        // --- End of Code Block ---
        Optional<String> currentUserEmailOptional = SecurityUtil.getCurrentUsernameLogin();
        if (currentUserEmailOptional.isEmpty()) {
            throw ApiException.create(HttpStatus.UNAUTHORIZED).withMessage("Cần đăng nhập để thực hiện chức năng này");
        }
        String userName = currentUserEmailOptional.get();
        User currentUser = userRepository.findByUserName(userName);
        if (currentUser != null) {
            Booking booking = new Booking();
            booking.setUser(currentUser);
            booking.setBookingDate(request.getBookingDate());
            booking.setBookingTime(bookingTime);
            booking.setReason(request.getReason());
            booking.setDoctor(doctor);
            booking.setPatientName(request.getPatientName());
            booking.setPhoneNumber(request.getPhoneNumber());
            booking.setEmail(request.getEmail());
            booking.setGender(request.getGender());
            booking.setAddress(request.getAddress());
            booking.setBookingType(request.getBookingType());
            bookingRepository.save(booking);

            // Send email after booking success
            try {
                String bookingTemplate = IOUtils.readFileFromResource(BOOKING_CONFIRMATION_TEMPLATE);
                bookingTemplate = bookingTemplate.replace("booking_id", booking.getId().toString());
                bookingTemplate = bookingTemplate.replace("booking_date", TimeUtil.formatDate(booking.getBookingDate(), "dd/MM/yyyy"));
                bookingTemplate = bookingTemplate.replace("booking_time", TimeUtil.getFormattedToTime(bookingTime.getFromTime()));

                emailService.sendHtmlEmail(request.getEmail(), "Xác Nhận Đặt Khám", bookingTemplate);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            return true;
        }
        return false;
    }

    @Override
    public DoctorBookingDTO getDoctorBooking(Long doctorId, Date bookingDate) {
        Set<Long> bookingTimeIds = bookingRepository.findAllByBookingDateBetweenAndDoctorId(
                        TimeUtil.getStartTimeOfDate(bookingDate),
                        TimeUtil.getEndTimeOfDate(bookingDate),
                        doctorId)
                .stream()
                .map(booking -> booking.getBookingTime().getId())
                .collect(Collectors.toSet());

        List<BookingPrice> bookingPrices = doctorRepository
                .findById(doctorId).orElseThrow(() -> ApiException.create(HttpStatus.BAD_REQUEST)
                        .withMessage("Không tìm thấy bác sĩ với id:" + doctorId))
                .getBookingPrice();

        DoctorBookingDTO doctorBookingDto = new DoctorBookingDTO();
        doctorBookingDto.setDoctorId(doctorId);
        doctorBookingDto.setBookingDate(bookingDate);
        List<BookingTimeInfoDTO> bookingTimeInfoDtoList = doctorBookingDto.getBookingTimeInfoDTOS();

        for (BookingPrice bookingPrice : bookingPrices) {
            BookingTimeInfoDTO bookingTimeInfoDTO = getBookingTimeInfoDTO(bookingPrice, bookingTimeIds);
            bookingTimeInfoDtoList.add(bookingTimeInfoDTO);
        }

        return doctorBookingDto;
    }

    @Override
    public List<BookingDTO> getBookingOfUser(Integer userId) {
        List<BookingDTO> bookingDTO = new ArrayList<>();
        List<Booking> result = bookingRepository.findByUserId(userId);
        List<BookingDTO> response = new ArrayList<>();
        for (Booking booking : result) {
            BookingDTO dto = bookingMapper.toDto(booking);
            dto.setDoctor(doctorMapper.toDto(booking.getDoctor()));
            dto.setBookingTime(bookingTimeMapper.toDto(booking.getBookingTime()));
            response.add(dto);
        }
        return response;
    }

    private static BookingTimeInfoDTO getBookingTimeInfoDTO(BookingPrice bookingPrice, Set<Long> bookingTimeIds) {
        BookingTimeInfoDTO bookingTimeInfoDTO = new BookingTimeInfoDTO();
        bookingTimeInfoDTO.setId(bookingPrice.getBookingTime().getId());
        bookingTimeInfoDTO.setPrice(bookingPrice.getPrice());
        bookingTimeInfoDTO.setFromTime(bookingPrice.getBookingTime().getFromTime());
        bookingTimeInfoDTO.setToTime(bookingPrice.getBookingTime().getToTime());
        bookingTimeInfoDTO.setBooked(bookingTimeIds.contains(bookingPrice.getBookingTime().getId()));
        return bookingTimeInfoDTO;
    }
}
