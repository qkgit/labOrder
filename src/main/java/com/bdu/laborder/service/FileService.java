package com.bdu.laborder.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author Qi
 * @data 2021/4/29 10:43
 */
public interface FileService {

    String storeFile(MultipartFile file,String id);

    Resource loadFileAsResource(String fileName);
}
