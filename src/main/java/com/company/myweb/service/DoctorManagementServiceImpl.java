package com.company.myweb.service;

import com.company.myweb.dto.DoctorDTO;
import com.company.myweb.entity.Doctor;
import com.company.myweb.entity.common.ApiException;
import com.company.myweb.entity.common.ApiPage;
import com.company.myweb.payload.request.AddDoctorRequest;
import com.company.myweb.repository.DoctorRepository;
import com.company.myweb.service.interfaces.IDoctorManagementService;
import com.company.myweb.utils.PageUtil;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DoctorManagementServiceImpl implements IDoctorManagementService {

    private static final Logger log = LoggerFactory.getLogger(DoctorManagementServiceImpl.class);
    private final DoctorRepository doctorRepository;

    public DoctorManagementServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public ApiPage<DoctorDTO> getAllDoctor(String query, Pageable pageable) {
        Page<Doctor> doctorPage;

        if (query == null || query.isEmpty()) {
            doctorPage = doctorRepository.findAll(pageable);
        } else {
            doctorPage = doctorRepository.findByFullNameContainingIgnoreCase(query, pageable);
        }

        Page<DoctorDTO> dtoPage = doctorPage.map(this::convertToDoctorDTO);

        return PageUtil.convert(dtoPage);
    }

    @Override
    public DoctorDTO getDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> ApiException.create(HttpStatus.BAD_REQUEST).withMessage("Không tìm thấy bác sĩ với id:" + id));
        return convertToDoctorDTO(doctor);
    }

    @Override
    public Boolean addDoctor(AddDoctorRequest request) {
        Doctor doctor = new Doctor();
        doctor.setFullName(request.getFullName().trim());
        doctor.setIntroduction(request.getIntroduction().trim());
        doctor.setLongIntroduction(request.getLongIntroduction().trim());
        doctor.setTitle(request.getTitle().trim());
        doctor.setImage(request.getBase64Image());
        doctorRepository.save(doctor);
        return true;
    }

    @Override
    public Boolean updateDoctor(Long id, AddDoctorRequest request) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> ApiException.create(HttpStatus.BAD_REQUEST).withMessage("Không tìm thấy bác sĩ với id:" + id));

        doctor.setFullName(request.getFullName().trim());
        doctor.setIntroduction(request.getIntroduction().trim());
        doctor.setLongIntroduction(request.getLongIntroduction().trim());
        doctor.setTitle(request.getTitle().trim());
        doctor.setImage(request.getBase64Image());

        doctorRepository.save(doctor);
        return true;
    }

    @Override
    public Boolean removeDoctor(Long id) {
        try {
            Doctor doctor = doctorRepository.findById(id)
                    .orElseThrow(() -> ApiException.create(HttpStatus.BAD_REQUEST).withMessage("Không tìm thấy bác sĩ với id:" + id));
            doctorRepository.delete(doctor);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    private DoctorDTO convertToDoctorDTO(Doctor doctor) {
        DoctorDTO dto = new DoctorDTO();
        dto.setId(doctor.getId());
        dto.setFullName(doctor.getFullName());
        dto.setIntroduction(doctor.getIntroduction());
        dto.setLongIntroduction(doctor.getLongIntroduction());
        dto.setTitle(doctor.getTitle());
        dto.setImage(doctor.getImage());
        return dto;
    }
}
