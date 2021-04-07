/*
 * Copyright 2018-2021, ranke (213539@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.klw8.alita.ftp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.*;
import org.springframework.util.Assert;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: FtpClient
 * @Description: FTP客户端工具
 * @date 2020/6/4 16:00
 */
@Slf4j
public class FtpClient {

    /**
     * @author klw(213539 @ qq.com)
     * @Description: 字符集编码
     */
    private String charSet = "UTF-8";

    /**
     * @author klw(213539 @ qq.com)
     * @Description: 远端文件编码
     */
    private String remoteCharSet = "iso-8859-1";

    /**
     * @author klw(213539 @ qq.com)
     * @Description: 字节数组长度
     */
    private int byteSize = 1024;


    /**
     * @author klw(213539 @ qq.com)
     * @Description: 缓冲区大小
     */
    public static final int IO_BUFFERED = 1024;

    /**
     * @author klw(213539 @ qq.com)
     * @Description: 路径中的分隔符 /
     */
    private String separated = "/";


    /**
     * @author klw(213539 @ qq.com)
     * @Description: FTP服务端IP
     */
    private String host;

    /**
     * @author klw(213539 @ qq.com)
     * @Description: FTP服务端端口
     */
    private int port;

    /**
     * @author klw(213539 @ qq.com)
     * @Description: FTP服务端用户名
     */
    private String username;

    /**
     * @author klw(213539 @ qq.com)
     * @Description: FTP服务端用户密码
     */
    private String password;

    /**
     * @author klw(213539 @ qq.com)
     * @Description: 连接FTP服务端时使用的代理IP
     */
    private String proxyHost;

    /**
     * @author klw(213539 @ qq.com)
     * @Description: 连接FTP服务端时使用的代理端口
     */
    private int porxyPort;

    /**
     * @return void
     * @author klw(213539 @ qq.com)
     * @Description: 设置客户端字符编码
     * @Date 2020/6/5 16:15
     * @param: charSet
     */
    public void setCharSet(String charSet) {
        this.charSet = charSet;
    }

    /**
     * @return java.lang.String
     * @author klw(213539 @ qq.com)
     * @Description: 获取客户端字符编码
     * @Date 2020/6/5 16:15
     * @param:
     */
    public String getCharSet() {
        return charSet;
    }

    /**
     * @return void
     * @author klw(213539 @ qq.com)
     * @Description: 设置FTP服务端字符编码
     * @Date 2020/6/5 16:15
     * @param: serverCharSet
     */
    public void setServerCharSet(String serverCharSet) {
        this.remoteCharSet = serverCharSet;
    }

    /**
     * @return java.lang.String
     * @author klw(213539 @ qq.com)
     * @Description: 获取FTP服务端字符编码
     * @Date 2020/6/5 16:16
     * @param:
     */
    public String getServerCharSet() {
        return this.remoteCharSet;
    }


    /**
     * @return top.klw8.alita.ftp.FtpClient
     * @author klw(213539 @ qq.com)
     * @Description: 连接并获得一个ftp客户端
     * @Date 2020/6/5 9:15
     * @param: host
     * @param: port
     * @param: username
     * @param: password
     */
    public static FtpClient creatClient(String host, String port, String username, String password) {
        Assert.hasText(host, "host不能为空");
        Assert.hasText(port, "port不能为空");
        Assert.hasText(username, "username不能为空");
        Assert.hasText(password, "password不能为空");
        return new FtpClient(host, port, username, password);
    }


    /**
     * @param host     主机名
     * @param port     端口
     * @param username 用户名
     * @param password 密码
     * @author klw(213539 @ qq.com)
     * @Description: 无参构造
     * @Date 2020/6/5 9:20
     */
    private FtpClient(String host, String port, String username, String password) {
        this.host = host;
        this.port = Integer.valueOf(port);
        this.username = username;
        this.password = password;
    }

    /**
     * @return void
     * @author klw(213539 @ qq.com)
     * @Description: 设置代理
     * @Date 2020/6/5 16:12
     * @param: proxyHost
     * @param: porxyPort
     */
    public void useProxy(String proxyHost, String porxyPort) {
        this.proxyHost = proxyHost;
        this.porxyPort = Integer.valueOf(porxyPort);
    }

    /**
     * @return org.apache.commons.net.ftp.FTPClient
     * @author klw(213539 @ qq.com)
     * @Description: 初始化一个Apache FtpClient 并创建连接
     * @Date 2020/6/5 16:16
     * @param:
     */
    private FTPClient initApacheFtpClient() {
        FTPClient client;
        try {
            // 判断是否有代理数据 实例化FTP客户端
            if (!StringUtils.isEmpty(proxyHost) && porxyPort > 0) {
                // 实例化有代理的FTP客户端
                client = new FTPHTTPClient(proxyHost, porxyPort);
            } else {
                // 实例化FTP客户端
                client = new FTPClient();

            }
            client.connect(host, port);
            client.setControlEncoding(this.charSet);
            if (FTPReply.isPositiveCompletion(client.getReplyCode()) && client.login(username, password)) {
                // 设置被动模式
                client.enterLocalPassiveMode();
                return client;
            }
            disconnect(client);
            return null;
        } catch (IOException e) {
            log.error("FTPClient初始化错误!", e);
            return null;
        }

    }


    /**
     * @return void
     * @author klw(213539 @ qq.com)
     * @Description: 断开与远程服务器的连接
     * @Date 2020/6/5 9:53
     * @param:
     */
    private void disconnect(FTPClient client) throws IOException {
        if (client.isConnected()) {
            client.disconnect();
        }
    }

    /**
     * @author klw(213539 @ qq.com)
     * @Description: 上传文件到FTP服务器，支持断点续传
     * @Date 2020/6/5 9:53
     * @param: local 本地文件名称/绝对路径
     * @param: remote 远程文件路径
     * @return 上传是否成功
     */
    public boolean upload(String local, String remote) throws IOException {
        FTPClient ftpClient = this.initApacheFtpClient();
        // 设置以二进制流的方式传输
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.setControlEncoding(this.charSet);
        // 对远程目录的处理
        String remoteFileName = remote;
        if (remote.contains(this.separated)) {
            remoteFileName = remote.substring(remote.lastIndexOf(this.separated) + 1);
            // 服务器中创建目录,创建失败直接返回
            if (!this.createDirecroty(remote, ftpClient)) {
                this.log.info("服务器中创建目录失败");
                return false;
            }
        }
        // 返回状态
        boolean result;
        // 检查远程是否存在文件
        FTPFile[] files = ftpClient
                .listFiles(new String(remoteFileName.getBytes(this.charSet), this.remoteCharSet));
        if (files.length == 1) {
            long remoteSize = files[0].getSize();
            File f = new File(local);
            long localSize = f.length();
            if (remoteSize >= localSize) {
                this.log.info("远端文件大于等于本地文件大小,无需上传,终止上传");
                result = true;
            } else {
                // 尝试移动文件内读取指针,实现断点续传
                result = uploadFile(remoteFileName, f, ftpClient, remoteSize);

                if(!result) {
                    // 如果断点续传没有成功，则删除服务器上文件，重新上传
                    result = this.reUploadFile(ftpClient, remoteFileName, f);
                }
            }
        } else {
            result = uploadFile(remoteFileName, new File(local), ftpClient, 0);
        }

        this.disconnect(ftpClient);
        return result;
    }

    /**
     * @return java.lang.String
     * @author klw(213539 @ qq.com)
     * @Description: 如果断点续传没有成功，则删除服务器上文件，重新上传
     * @Date 2020/6/5 9:56
     * @param: result
     * @param: remoteFileName
     * @param: f
     */
    private boolean reUploadFile(FTPClient ftpClient, String remoteFileName, File f) throws IOException {
        boolean rs = false;
        if (!ftpClient.deleteFile(remoteFileName)) {
            this.log.info("删除远端文件失败");
            return rs;
        }
        rs = uploadFile(remoteFileName, f, ftpClient, 0);
        return rs;
    }

    /**
     * @param remote 远程文件路径
     * @param local  本地文件路径
     * @return 下载是否成功
     * @author klw(213539 @ qq.com)
     * @Description: 从FTP服务器上下载文件, 支持断点续传，下载百分比汇报
     * @Date 2020/6/5 9:56
     */
    public boolean download(String remote, String local) throws IOException {
        FTPClient ftpClient = this.initApacheFtpClient();
        // 设置以二进制方式传输
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

        // 返回状态
        boolean result;

        // 检查远程文件是否存在
        FTPFile[] files = ftpClient.listFiles(new String(remote.getBytes(this.charSet), this.remoteCharSet));
        if (files.length != 1) {
            this.log.info("远程文件不存在");
            result = false;
        } else {
            result = this.download(ftpClient, files, local, remote);
        }
        this.disconnect(ftpClient);
        return result;
    }

    /**
     * @param files  文件列表
     * @param local  本地路径
     * @param remote 远程路径
     * @return 结果
     * @author klw(213539 @ qq.com)
     * @Description: 下载文件
     * @Date 2020/6/5 9:56
     */
    private boolean download(FTPClient ftpClient, FTPFile[] files, String local, String remote) throws IOException {
        boolean result;
        // 获得远程文件的大小
        long lRemoteSize = files[0].getSize();

        // 获得本地文件对象(不存在,则创建)
        File f = new File(local);

        // 本地存在文件，进行断点下载
        if (f.exists()) {

            // 获得本地文件的长度
            long localSize = f.length();

            // 判断本地文件大小是否大于远程文件大小
            if (localSize >= lRemoteSize) {
                this.log.info("本地文件大于等于远程文件,无需下载,下载中止");
                result = false;
            } else {
                result = this.downloaddd(ftpClient, localSize, f, remote, lRemoteSize);
            }
        } else {
            result = this.downloadCreate(ftpClient, f, remote, lRemoteSize);
        }
        return result;
    }

    /**
     * @param localSize   文件尺寸
     * @param f           文件
     * @param remote      远程目录
     * @param lRemoteSize 远程文件尺寸
     * @return 结果
     * @author klw(213539 @ qq.com)
     * @Description: 下载
     * @Date 2020/6/5 9:57
     */
    private boolean downloaddd(FTPClient ftpClient, long localSize, File f, String remote, long lRemoteSize) throws IOException {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {

            // 设置开始点
            ftpClient.setRestartOffset(localSize);

            // 获得流(进行断点续传，并记录状态)
            bos = new BufferedOutputStream(new FileOutputStream(f, true));
            bis = new BufferedInputStream(
                    ftpClient.retrieveFileStream(new String(remote.getBytes(this.charSet), this.remoteCharSet)));

            // 字节流
            byte[] bytes;
            bytes = new byte[this.byteSize];

            // 开始下载
            int c;
            while ((c = bis.read(bytes)) != -1) {
                bos.write(bytes, 0, c);
            }
        } catch (Exception ex){
            log.error("FTP文件下载出现错误", ex);
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (bos != null) {
                bos.flush();
                bos.close();
            }
            ftpClient.completePendingCommand();
        }
        return true;
    }

    /**
     * @param f           文件
     * @param remote      远程路径
     * @param lRemoteSize 远程文件尺寸
     * @return 结果
     * @author klw(213539 @ qq.com)
     * @Description: 下载
     * @Date 2020/6/5 9:57
     */
    private boolean downloadCreate(FTPClient ftpClient, File f, String remote, long lRemoteSize) throws IOException {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            // 获得流
            bos = new BufferedOutputStream(new FileOutputStream(f));
            bis = new BufferedInputStream(
                    ftpClient.retrieveFileStream(new String(remote.getBytes(this.charSet), this.remoteCharSet)));

            // 开始下载
            int c;
            byte[] bytes;
            bytes = new byte[this.byteSize];
            while ((c = bis.read(bytes)) != -1) {
                bos.write(bytes, 0, c);
            }

        } catch (Exception ex){
            log.error("FTP文件下载出现错误", ex);
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (bos != null) {
                bos.flush();
                bos.close();
            }
            ftpClient.completePendingCommand();
        }

        return true;
    }

    /**
     * @return org.apache.commons.net.ftp.FTPFile[]
     * @author klw(213539 @ qq.com)
     * @Description: 返回远程文件列表
     * @Date 2020/6/5 9:58
     * @param: remotePath
     */
    public FTPFile[] getremoteFiles(String remotePath) throws IOException {
        FTPClient ftpClient = this.initApacheFtpClient();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE); // 设置以二进制方式传输
        FTPFile[] result = ftpClient.listFiles(remotePath);
        this.disconnect(ftpClient);
        return result;
    }

    /**
     * @return boolean
     * @author klw(213539 @ qq.com)
     * @Description: 删除ftp上的文件
     * @Date 2020/6/5 9:58
     * @param: remotePath
     */
    public boolean removeFile(String remotePath) throws IOException {
        boolean flag = false;
        FTPClient ftpClient = this.initApacheFtpClient();
        if (ftpClient != null) {

            // 设置以二进制方式传输
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            flag = ftpClient.deleteFile(new String(remotePath.getBytes(this.charSet), this.remoteCharSet));
        }
        this.disconnect(ftpClient);
        return flag;
    }

    /**
     * @param remoteFile 远程文件名，在上传之前已经将服务器工作目录做了改变
     * @param localFile  本地文件File句柄，绝对路径
     * @param fc         FTPClient引用
     * @return 上传是否成功
     * @author klw(213539 @ qq.com)
     * @Description: 上传文件到服务器, 新上传和断点续传
     * @Date 2020/6/5 9:59
     */
    private boolean uploadFile(String remoteFile, File localFile, FTPClient fc, long remoteSize) throws IOException {
        RandomAccessFile raf = null;
        BufferedOutputStream bos = null;
        try {
            raf = new RandomAccessFile(localFile, "r");
            bos = new BufferedOutputStream(
                    fc.appendFileStream(new String(remoteFile.getBytes(this.charSet), this.remoteCharSet)),
                    IO_BUFFERED);

            // 断点续传
            if (remoteSize > 0) {
                fc.setRestartOffset(remoteSize);
                raf.seek(remoteSize);
            }

            // 开始上传
            byte[] bytes;
            bytes = new byte[this.byteSize];
            int c;
            while (-1 != (c = raf.read(bytes))) {
                bos.write(bytes, 0, c);
            }

        } catch (Exception ex){
            log.error("FTP文件上传时发生错误: ", ex);
            return false;
        } finally {
            if (bos != null) {
                bos.flush();
                bos.close();
            }
            if (raf != null) {
                raf.close();
            }
            fc.completePendingCommand();
        }
        return true;
    }

    /**
     * @param remote 远程服务器文件绝对路径
     * @param fc     FTPClient对象
     * @return 目录创建是否成功
     * @author klw(213539 @ qq.com)
     * @Description: 递归创建远程服务器目录
     * @Date 2020/6/5 9:59
     */
    private boolean createDirecroty(String remote, FTPClient fc) throws IOException {
        boolean status = true;
        String directory = remote.substring(0, remote.lastIndexOf(this.separated) + 1);
        if (!this.separated.equalsIgnoreCase(directory)
                && !fc.changeWorkingDirectory(new String(directory.getBytes(this.charSet), this.remoteCharSet))) {
            // 如果远程目录不存在，则递归创建远程服务器目录
            status = this.createDirecroty(directory, fc, remote);
        }
        return status;
    }

    /**
     * @return java.lang.String
     * @author klw(213539 @ qq.com)
     * @Description: 创建目录
     * @Date 2020/6/5 10:00
     * @param: directory
     * @param: fc
     * @param: remote
     */
    private boolean createDirecroty(String directory, FTPClient fc, String remote) throws IOException {
        int start = 0;
        int end;
        if (directory.startsWith(this.separated)) {
            start = 1;
        }
        end = directory.indexOf(this.separated, start);
        while (true) {
            String subDirectory = new String(remote.substring(start, end).getBytes(this.charSet), this.remoteCharSet);
            if (!fc.changeWorkingDirectory(subDirectory)) {
                if (fc.makeDirectory(subDirectory)) {
                    fc.changeWorkingDirectory(subDirectory);
                } else {
                    this.log.info("创建目录失败");
                    return false;
                }
            }

            start = end + 1;
            end = directory.indexOf(this.separated, start);

            // 检查所有目录是否创建完毕
            if (end <= start) {
                break;
            }
        }
        return true;
    }

}
