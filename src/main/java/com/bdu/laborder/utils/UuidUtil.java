package com.bdu.laborder.utils;

import java.util.UUID;

/**
 * @Author Qi
 * @data 2021/9/27 10:50
 */
public class UuidUtil {
    public UuidUtil() {
    }

    public static final String getUuid() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-","");
    }

}
