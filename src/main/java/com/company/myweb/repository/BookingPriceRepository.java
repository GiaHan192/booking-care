package com.company.myweb.repository;

import com.company.myweb.entity.BookingPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingPriceRepository extends JpaRepository<BookingPrice, Long> {
}
