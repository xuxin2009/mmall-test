package com.mmall.utils;


import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2018/9/14.
 */

public class FTPUtil {

    private static final Logger logger = LoggerFactory.getLogger(FTPUtil.class);

    private static String ftpIp = PropertiesUtil.getProperty("ftp.server.ip");
    private static String ftpUser = PropertiesUtil.getProperty("ftp.user");
    private static String ftpPwd = PropertiesUtil.getProperty("ftp.pass");

    private String ip;
    private int port;
    private String user;
    private String pwd;
    private FTPClient ftpClient;

    public FTPUtil( String ip, int port, String user,String pwd) {
        this.pwd = pwd;
        this.ip = ip;
        this.port = port;
        this.user = user;
    }

    public static String getFtpIp() {
        return ftpIp;
    }

    public static void setFtpIp(String ftpIp) {
        FTPUtil.ftpIp = ftpIp;
    }

    public static String getFtpUser() {
        return ftpUser;
    }

    public static void setFtpUser(String ftpUser) {
        FTPUtil.ftpUser = ftpUser;
    }

    public static String getFtpPwd() {
        return ftpPwd;
    }

    public static void setFtpPwd(String ftpPwd) {
        FTPUtil.ftpPwd = ftpPwd;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }

    /**
     * 向FTP服务器上传文件
     * @param fileList
     * @return
     */
    public static boolean uploadFile(List<File> fileList) throws IOException {
        FTPUtil ftpUtil = new FTPUtil(ftpIp,21,ftpUser,ftpPwd);
        logger.info("开始连接FTP服务器");
        boolean result = ftpUtil.uploadFile("/ftpfile",fileList);

        logger.info("开始连接FTP服务器，结束上传，上传结果：{}",result);
        return result;
    }

    /**
     *文件上传的具体操作
     * @param remotePath
     * @return
     */
    private  boolean uploadFile(String remotePath,List<File> fileList) throws IOException {
        boolean uploaded = false;
        FileInputStream  fis = null;
        //连接FTP服务器
       if( connectFTPServer(this.ip,this.port,this.user,this.pwd))
        {
            try {
                logger.info("文件目录是否已存在:"+ftpClient.changeWorkingDirectory(remotePath));
                ftpClient.changeWorkingDirectory(remotePath);//更改FTP会话的当前工作目录
                ftpClient.setBufferSize(1024);//设置缓存大小
                ftpClient.setControlEncoding("UTF-8");//设置字符集utf-8
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);//设置成二进制文件
                ftpClient.enterLocalPassiveMode();//设置FTP被动模式
                for(File fileItem : fileList)//开始循环上传文件
                {
                    fis = new FileInputStream(fileItem);
                    ftpClient.storeFile(fileItem.getName(),fis);
                }
                uploaded = true;
            } catch (IOException e) {

                logger.error("上传文件异常",e);
            }finally {
                    fis.close();
                    ftpClient.disconnect();
            }
        }
        return uploaded;
    }

    /**
     * 连接FTP服务器
     * @param ip
     * @param port
     * @param user
     * @param password
     * @return
     */
    private  boolean connectFTPServer(String ip ,int port,String user,String password)
    {
        boolean isSuccess = false;
        ftpClient = new FTPClient();
        try {
            int reply;
            ftpClient.connect(ip);
            isSuccess = ftpClient.login(user,password);
            logger.info("FTP是否登陆成功：{}",isSuccess);

            reply = ftpClient.getReplyCode();
            if(!FTPReply.isPositiveCompletion(reply))
            {
                ftpClient.disconnect();
                return false;
            }

        } catch (IOException e) {
            logger.error("连接FTP服务器异常",e);
        }
        return isSuccess;
    }

}
