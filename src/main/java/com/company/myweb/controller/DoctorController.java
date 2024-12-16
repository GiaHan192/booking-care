package com.company.myweb.controller;

import com.company.myweb.dto.DoctorDTO;
import com.company.myweb.entity.common.ApiPage;
import com.company.myweb.entity.common.ApiResponse;
import com.company.myweb.payload.request.AddDoctorRequest;
import com.company.myweb.service.interfaces.IDoctorManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final IDoctorManagementService doctorService;

    @Autowired
    public DoctorController(IDoctorManagementService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ApiPage<DoctorDTO>>> getAllDoctors(
            @RequestParam(value = "query", required = false) String query,
            Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(doctorService.getAllDoctor(query, pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorDTO>> getDoctor(@PathVariable Long id) {
        DoctorDTO doctor = doctorService.getDoctor(id);
        if (doctor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.success(doctor));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> addDoctor(@RequestBody AddDoctorRequest request) {
        Boolean isAdded = doctorService.addDoctor(request);
        if (isAdded) {
            return ResponseEntity.ok(ApiResponse.success("Thêm bác sĩ thành công"));
        }
        return ResponseEntity.badRequest().body(ApiResponse.failed("Thêm bác sĩ thất bại"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateDoctor(@PathVariable Long id, @RequestBody AddDoctorRequest request) {
        Boolean isUpdated = doctorService.updateDoctor(id, request);
        if (isUpdated) {
            return ResponseEntity.ok(ApiResponse.success("Cập nhật bác sĩ thành công"));
        }
        return ResponseEntity.badRequest().body(ApiResponse.failed("Cập nhật bác sĩ thất bại"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteDoctor(@PathVariable Long id) {
        Boolean removingResult = doctorService.removeDoctor(id);
        if (removingResult) {
            return ResponseEntity.ok(ApiResponse.success("Xóa bác sĩ thành công"));
        } else {
            return ResponseEntity.badRequest().body(ApiResponse.failed("Xóa bác sĩ thất bại"));
        }
    }
}
