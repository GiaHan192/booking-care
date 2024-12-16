package com.company.myweb.service.interfaces;

import com.company.myweb.dto.UserDTO;
import com.company.myweb.payload.request.SignUpRequest;

import java.util.List;

public interface ILoginService {
    List<UserDTO> getAllUser();

    boolean checkLogin(String username, String password);

    boolean addUser(SignUpRequest signUpRequest);

    UserDTO getUserByUserName(String userName);
}
