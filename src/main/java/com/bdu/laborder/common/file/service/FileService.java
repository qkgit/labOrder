package com.bdu.laborder.common.file.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author Qi
 * @data 2021/4/29 10:43
 */
public interface FileService {

    String storeFile(MultipartFile file);

    Resource loadFileAsResource(String fileName);
}
