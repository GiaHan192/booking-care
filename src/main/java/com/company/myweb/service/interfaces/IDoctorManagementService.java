package com.company.myweb.service.interfaces;

import com.company.myweb.dto.DoctorDTO;
import com.company.myweb.entity.common.ApiPage;
import com.company.myweb.payload.request.AddDoctorRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDoctorManagementService {
    ApiPage<DoctorDTO> getAllDoctor(String query, Pageable pageable);

    DoctorDTO getDoctor(Long id);

    Boolean addDoctor(AddDoctorRequest request);

    Boolean updateDoctor(Long id, AddDoctorRequest request);

    Boolean removeDoctor(Long id);
}
