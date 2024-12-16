package com.company.myweb.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctors")
@Data
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "full_name")
    private String fullName = "";
    @Column(name = "introduction")
    private String introduction = "";
    @Column(name = "title")
    private String title = "";
    @Lob
    @Column(name = "image", length = 99999)
    private String image = "";
    @OneToMany(mappedBy = "doctor")
    private List<BookingPrice> bookingPrice = new ArrayList<>();
    @OneToMany(mappedBy = "doctor")
    private List<Booking> bookings = new ArrayList<>();
    @Column(name = "long_introduction", length = 99999)
    @Lob
    private String longIntroduction = "";
}
