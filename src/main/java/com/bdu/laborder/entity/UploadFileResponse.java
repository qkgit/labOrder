package com.bdu.laborder.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Qi
 * @data 2021/4/29 10:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileResponse implements Serializable {
    private String fileName;

    private String fileDownloadUri;

    private String fileType;

    private long fileSize;
}
