package com.company.myweb.repository;

import com.company.myweb.entity.Booking;
import com.company.myweb.entity.BookingTime;
import com.company.myweb.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByBookingDateBetweenAndDoctorId(Date start, Date endDate, Long doctorId);

    Optional<Integer> countByBookingDateBetweenAndDoctorAndBookingTime(Date startTimeOfDate, Date endTimeOfDate, Doctor doctor, BookingTime bookingTime);

    List<Booking> findByUserId(Integer userId);
}
