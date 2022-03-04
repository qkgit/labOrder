package com.bdu.laborder.common.core.domain.service;

import com.bdu.laborder.common.constant.UserConstants;
import com.bdu.laborder.common.core.domain.entity.SysRole;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Title
 * @Author Qi
 * @data 2022/3/4 11:14
 */
public class UserService {

    public static boolean isAdmin(List<SysRole> roles) {
        if (roles != null){
            return roles.stream()
                    .map(r -> r.getRoleId())
                    .collect(Collectors.toList())
                    .contains(UserConstants.ADMIN_ROLE_ID);
        }
        return false;
    }
}
