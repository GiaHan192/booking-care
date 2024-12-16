package com.company.myweb.service.interfaces;

import com.company.myweb.dto.UserDTO;
import com.company.myweb.entity.common.ApiPage;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {
    ApiPage<UserDTO> getAllUser(String query, Pageable pageable);

    Boolean editActivateState(Integer id, Boolean isActivate);
}
