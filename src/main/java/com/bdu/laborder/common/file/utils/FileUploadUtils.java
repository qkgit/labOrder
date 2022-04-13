package com.bdu.laborder.common.file.utils;

import com.bdu.laborder.common.constant.Constant;
import com.bdu.laborder.config.FileStorageConfig;
import com.bdu.laborder.exception.BaseException;
import com.bdu.laborder.exception.file.InvalidExtensionException;
import com.bdu.laborder.utils.DateUtils;
import com.bdu.laborder.utils.StringUtils;
import com.bdu.laborder.utils.UuidUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Title 文件上传工具类
 * @Author Qi
 * @data 2022/4/11 14:42
 */
public class FileUploadUtils {

    /**
     * 默认大小 50M  (-1不进行限制)
     */
    public static final long DEFAULT_MAX_SIZE = 50 * 1024 * 1024;

    /**
     * 默认的文件名最大长度 100
     */
    public static final int DEFAULT_FILE_NAME_LENGTH = 100;

    /**
     * 默认上传的地址
     */
    private static String defaultBaseDir = FileStorageConfig.getUploadDir();


    public static void setDefaultBaseDir(String defaultBaseDir) {
        FileUploadUtils.defaultBaseDir = defaultBaseDir;
    }

    public static String getDefaultBaseDir() {
        return defaultBaseDir;
    }


    /**
     * 以默认配置进行文件上传
     *
     * @param file 上传的文件
     * @return 文件名称
     * @throws Exception
     */
    public static final String upload(MultipartFile file) throws IOException {
        try {
            return upload(getDefaultBaseDir(), file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * 根据文件路径上传
     *
     * @param baseDir 相对应用的基目录
     * @param file    上传的文件
     * @return 文件名称
     * @throws IOException
     */
    public static final String upload(String baseDir, MultipartFile file) throws IOException {
        try {
            return upload(baseDir, file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     *  文件上传
     *
     * @param baseDir            相对应用的基目录
     * @param file               上传的文件
     * @param allowedExtension   上传文件类型
     * @return 返回上传成功的文件名
     */
    public static final String upload(String baseDir, MultipartFile file, String[] allowedExtension) throws
            BaseException, InvalidExtensionException, IOException {
        // 校验文件
        assertAllowed(file,allowedExtension);
        // 上传文件
        String filePathName = extractFilePathName(file);
        File desc = getAbsoluteFile(baseDir, filePathName);
        file.transferTo(desc);
        // 获取上传后文件名
        return getPathFileName(baseDir,filePathName);
    }

    /**
     * 获取文件路径
     * @param uploadDir
     * @param fileName
     * @return
     * @throws IOException
     */
    public static final String getPathFileName(String uploadDir, String fileName) throws IOException {
        int dirLastIndex = FileStorageConfig.getUploadDir().length() + 1;
        String currentDir = StringUtils.substring(uploadDir, dirLastIndex);
        String pathFileName = Constant.RESOURCE_PREFIX + "/" + currentDir + "/" + fileName;
        return pathFileName;
    }

    /**
     *  获取上传位置文件
     * @param uploadDir
     * @param fileName
     * @return
     * @throws IOException
     */
    public static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException {
        File desc = new File(uploadDir + File.separator + fileName);

        if (!desc.exists()) {
            if (!desc.getParentFile().exists()) {
                desc.getParentFile().mkdirs();
            }
        }
        return desc;
    }

    /**
     * 编码文件名：
     *      {{当前上传年}}/{{当前上传年月}}/{{当前上传年日}}/{{文件名(uuid).后缀名}}
     * 如：
     *      2022/04/11/de54b1430ff946bfa6484d6ccdf0eb42.jpg
     */
    public static final String extractFilePathName(MultipartFile file) {
        String extension = getExtension(file);
        String fileName = DateUtils.datePath() + "/" + UuidUtil.getUuid() + "." + extension;
        return fileName;
    }


    /**
     * 断言校验
     * @param file 上传的文件
     * @param allowedExtension 可接收文件类型
     */
    public static final void assertAllowed(MultipartFile file, String[] allowedExtension) throws
            InvalidExtensionException {
        // 文件名称长度校验
        int fileNameLength = file.getOriginalFilename().length();
        if (fileNameLength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
            throw new BaseException("文件名超出最大长度");
        }

        // 文件大小校验
        long fileSize = file.getSize();
        if(DEFAULT_MAX_SIZE != -1 && fileSize > DEFAULT_MAX_SIZE) {
            throw new BaseException("文件过大，上传失败！");
        }

        // 文件格式校验
        String fileName = file.getOriginalFilename();
        String extension = getExtension(file);
        if (allowedExtension != null && !isAllowedExtension(extension,allowedExtension)){
            if (allowedExtension == MimeTypeUtils.IMAGE_EXTENSION) {
                throw new InvalidExtensionException.InvalidImageExtensionException(allowedExtension, extension,
                        fileName);
            } else if (allowedExtension == MimeTypeUtils.FLASH_EXTENSION) {
                throw new InvalidExtensionException.InvalidFlashExtensionException(allowedExtension, extension,
                        fileName);
            } else if (allowedExtension == MimeTypeUtils.MEDIA_EXTENSION) {
                throw new InvalidExtensionException.InvalidMediaExtensionException(allowedExtension, extension,
                        fileName);
            } else if (allowedExtension == MimeTypeUtils.VIDEO_EXTENSION) {
                throw new InvalidExtensionException.InvalidVideoExtensionException(allowedExtension, extension,
                        fileName);
            } else {
                throw new InvalidExtensionException(allowedExtension, extension, fileName);
            }
        }
    }

    /**
     * 判断MIME类型是否是允许的MIME类型
     *
     * @param extension
     * @param allowedExtension
     * @return
     */
    public static final boolean isAllowedExtension(String extension, String[] allowedExtension) {
        for (String str : allowedExtension) {
            if (str.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件名的后缀
     *
     * @param file 表单文件
     * @return 后缀名
     */
    public static final String getExtension(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtils.isEmpty(extension)) {
            extension = MimeTypeUtils.getExtension(file.getContentType());
        }
        return extension;
    }

    /**
     * file 转换为 MultipartFile
     * @param filePath
     * @return
     */
    private  MultipartFile getMulFileByPath(String filePath)
    {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        String textFieldName = "textField";
        int num = filePath.lastIndexOf(".");
        String extFile = filePath.substring(num);
        FileItem item = factory.createItem(textFieldName, "text/plain", true,
                "MyFileName" + extFile);
        File newfile = new File(filePath);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try
        {
            FileInputStream fis = new FileInputStream(newfile);
            OutputStream os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, 8192))
                    != -1)
            {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        MultipartFile mfile = new CommonsMultipartFile(item);
        return mfile;
    }

}
