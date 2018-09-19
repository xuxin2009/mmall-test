package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2018/9/14.
 */
public interface FileService {

    String upload(MultipartFile file , String path);
}
