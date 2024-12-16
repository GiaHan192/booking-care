package com.company.myweb.service;

import com.company.myweb.dto.UserDTO;
import com.company.myweb.entity.User;
import com.company.myweb.entity.common.ApiException;
import com.company.myweb.entity.common.ApiPage;
import com.company.myweb.repository.UserRepository;
import com.company.myweb.service.interfaces.IUserService;
import com.company.myweb.utils.ConvertUtil;
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
public class UserService implements IUserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ApiPage<UserDTO> getAllUser(String query, Pageable pageable) {
        Page<User> listUser = userRepository.findAll(pageable);
        return PageUtil.convert(listUser.map(user -> {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUserName(user.getUsername());
            userDTO.setFullName(user.getFullName());
            userDTO.setActivate(user.isActivate());
            userDTO.setRoles(ConvertUtil.convertRoleToDTO(user.getRoles()));
            return userDTO;
        }));
    }

    @Override
    public Boolean editActivateState(Integer id, Boolean isActivate) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> ApiException.create(HttpStatus.BAD_REQUEST).withMessage("Không tìm thấy người dùng với id:" + id));
            user.setActivate(isActivate);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }
}
