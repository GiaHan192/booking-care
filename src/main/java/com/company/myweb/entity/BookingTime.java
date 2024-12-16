package com.company.myweb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalTime;
import java.util.Date;

@Data
@Entity
@Table(name = "booking_time")
@NoArgsConstructor
public class BookingTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalTime fromTime;
    private LocalTime toTime;

    public BookingTime(LocalTime fromTime, LocalTime toTime) {
        this.fromTime = fromTime;
        this.toTime = toTime;
    }
}
