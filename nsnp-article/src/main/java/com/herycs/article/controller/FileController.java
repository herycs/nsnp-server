package com.herycs.article.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.herycs.article.constant.Commons;
import com.herycs.common.entity.Result;
import com.herycs.common.entity.StatusCode;

import javax.servlet.http.HttpServletRequest;

import java.io.*;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName FileController
 * @Description [文件上传]
 * @Author ANGLE0
 * @Date 2021/4/26 16:52
 * @Version V1.0
 **/


@RestController
@CrossOrigin
@RequestMapping("/resource")
public class FileController {

    private static final SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy/MM/dd");

    @PostMapping("/upload")
    @ResponseBody
    public Result singleFileUpload(@RequestParam("file") MultipartFile file) throws Exception {

        if (file.isEmpty()) {
            throw new Exception("文件为空!");
        }

        String filename = upload(file);
        System.out.println(filename);

        String url = "http://192.168.1.119:9004/article/img/" + filename;

        return new Result(true, StatusCode.OK, "上传成功", url);
    }

    private String upload(MultipartFile file) throws Exception {
        int len = file.getOriginalFilename().length();
        if (len > Commons.DEFAULT_FILE_NAME_LENGTH) {
            throw new Exception("文件名超出限制!");
        }
        String extension = getExtension(file);
        if(!isValidExtension(extension)){
            throw new Exception("文件格式不正确");
        }
        // 自定义文件名
        String filename = getPathName(file);
        // 获取file对象
        File desc = getFile(filename);
        // 写入file
        file.transferTo(desc);
        return filename;
    }

    /**
     * 获取file对象
     */
    private File getFile(String filename) throws IOException {
        File file = new File(Commons.UPLOAD_FILE_PATH + "/" + filename);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        if(!file.exists()){
            file.createNewFile();
        }
        return file;
    }

    /**
     * 验证文件类型是否正确
     */
    private boolean isValidExtension(String extension) {
        for (String allowedExtension : Commons.ALLOWED_EXTENSIONS) {
            if(extension.equalsIgnoreCase(allowedExtension)){
                return true;
            }
        }
        return false;
    }

    /**
     * 此处自定义文件名,uuid + extension
     */
    private String getPathName(MultipartFile file) {
        String extension = getExtension(file);
        return dataFormat.format(System.currentTimeMillis()) + UUID.randomUUID().toString() + Commons.FILE_SPEARATOR + extension;
    }

    /**
     * 获取扩展名
     */
    private String getExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        return originalFilename.substring(originalFilename.lastIndexOf(Commons.FILE_SPEARATOR) + 1);
    }

}
