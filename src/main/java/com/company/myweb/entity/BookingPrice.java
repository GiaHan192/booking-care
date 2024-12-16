package com.company.myweb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Builder
@Data
@Entity
@Table(name = "booking_price")
@NoArgsConstructor
@AllArgsConstructor
public class BookingPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "booking_time_id")
    private BookingTime bookingTime;

    @Column(name = "price")
    private BigDecimal price;
}
