package com.company.myweb.utils;

import com.company.myweb.dto.RoleDTO;
import com.company.myweb.entity.Role;

public class ConvertUtil {
    public static RoleDTO convertRoleToDTO(Role role) {
        return ObjectUtil.copyProperties(role, new RoleDTO(), RoleDTO.class, true);
    }
}
