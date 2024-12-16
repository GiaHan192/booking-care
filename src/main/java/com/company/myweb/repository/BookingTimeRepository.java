package com.company.myweb.repository;

import com.company.myweb.entity.BookingTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingTimeRepository extends JpaRepository<BookingTime, Long> {
}
