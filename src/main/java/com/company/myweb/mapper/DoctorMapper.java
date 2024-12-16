package com.company.myweb.mapper;

import com.company.myweb.dto.DoctorDTO;
import com.company.myweb.entity.Doctor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    DoctorDTO toDto(Doctor doctor);
}
