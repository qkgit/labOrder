package com.bdu.laborder.common.core.domain.service;

import com.bdu.laborder.common.constant.UserConstants;
import com.bdu.laborder.common.core.domain.entity.SysRole;
import com.bdu.laborder.common.core.domain.entity.SysUser;
import com.bdu.laborder.utils.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Title
 * @Author Qi
 * @data 2022/3/4 11:14
 */
public class UserService {

    public static boolean isAdmin(SysUser user) {
        String[] roleIds = user.getRoleIds();
        if (StringUtils.isNotEmpty(roleIds)){
            return Arrays.asList(roleIds).contains(UserConstants.ADMIN_ROLE_ID);
        }
        return false;
    }
}
