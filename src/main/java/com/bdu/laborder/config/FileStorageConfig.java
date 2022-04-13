package com.bdu.laborder.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author Qi
 * @data 2021/4/29 10:36
 */
@Component
@ConfigurationProperties(prefix = "file")
public class FileStorageConfig {
    private static String uploadDir;

    public static String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    /**
     * 获取导入上传路径
     */
    public static String getImportPath(){
        return getUploadDir() + "/import";
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath(){
        return getUploadDir() + "/avatar";
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath(){
        return getUploadDir() + "/download";
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath(){
        return getUploadDir() + "/upload";
    }


}
