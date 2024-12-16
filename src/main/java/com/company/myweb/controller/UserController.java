package com.company.myweb.controller;

import com.company.myweb.dto.UserDTO;
import com.company.myweb.entity.common.ApiPage;
import com.company.myweb.entity.common.ApiResponse;
import com.company.myweb.payload.request.EditUserStateRequest;
import com.company.myweb.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final IUserService userServiceImp;

    public UserController(IUserService userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ApiPage<UserDTO>>> getAllUser(@RequestParam(required = false) String query, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(userServiceImp.getAllUser(query, pageable)));
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<ApiResponse<String>> changeActivateState(@PathVariable("id") Integer id, @RequestBody EditUserStateRequest request) {
        Boolean actionResult = userServiceImp.editActivateState(id, request.getActivate());
        if (actionResult) {
            return ResponseEntity.ok(ApiResponse.success("Thay đổi trạng thái người dùng thành công"));
        } else {
            return ResponseEntity.badRequest().body(ApiResponse.failed("Thay đổi trạng thái người dùng thất bại"));
        }
    }
}
