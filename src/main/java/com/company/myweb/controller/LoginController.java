package com.company.myweb.controller;

import com.company.myweb.dto.UserDTO;
import com.company.myweb.entity.common.ApiResponse;
import com.company.myweb.payload.request.SignInRequest;
import com.company.myweb.payload.request.SignUpRequest;
import com.company.myweb.service.interfaces.ILoginService;
import com.company.myweb.utils.JwtUtilsHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class LoginController {
    private final ILoginService loginServiceImp;
    private final JwtUtilsHelper jwtUtilsHelper;

    public LoginController(ILoginService loginServiceImp, JwtUtilsHelper jwtUtilsHelper) {
        this.loginServiceImp = loginServiceImp;
        this.jwtUtilsHelper = jwtUtilsHelper;
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<String>> signin(@RequestBody SignInRequest request) {
        if (loginServiceImp.checkLogin(request.getUserName(), request.getPassword())) {
            UserDTO userDto = loginServiceImp.getUserByUserName(request.getUserName());
            if (userDto != null) {
                String token = jwtUtilsHelper.generateToken(userDto);
                return ResponseEntity.ok(ApiResponse.success(token));
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.failed("Đăng nhập thất bại"));
        }
        return null;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> signup(@RequestBody SignUpRequest signUpRequest) {
        boolean result = loginServiceImp.addUser(signUpRequest);
        if (result) {
            return ResponseEntity.ok(ApiResponse.success("Tạo tài khoản thành công"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.failed("Tạo tài khoản thất bại"));
        }
    }

}
