package com.company.myweb.dto;

import lombok.Data;


@Data
public class DoctorDTO {

    private Long id;

    private String fullName = "";

    private String introduction = "";

    private String longIntroduction = "";

    private String title = "";

    private String image = "";

}
