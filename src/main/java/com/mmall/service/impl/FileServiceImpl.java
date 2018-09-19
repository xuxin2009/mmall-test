package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.FileService;
import com.mmall.utils.FTPUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Administrator on 2018/9/14.
 */
@Service
public class FileServiceImpl implements FileService{

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    public String upload(MultipartFile file ,String path)
    {
        String fileName = file.getOriginalFilename();
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
        logger.info("开始上传文件，上传文件的文件名：{}，新文件名：{},上传路径：{}",fileName,uploadFileName,path);

        File fileDir = new File(path);
        if(!fileDir.exists())
        {
        fileDir.setWritable(true);//赋予文件夹具有写操作
        fileDir.mkdirs();//创建文件目录
    }

        File targetFile = new File(path,uploadFileName);
        try {
            file.transferTo(targetFile);//文件开始上传，使用transferTo（dest）方法将上传文件写到upload文件目录下

            //targetFile文件上传到Ftp服务器
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));

            //上传到ftp服务器后，需要将upload文件加下的文件删除
            targetFile.delete();

        } catch (IOException e) {
            logger.error("文件上传异常",e);
        }
        return targetFile.getName();
    }
}
