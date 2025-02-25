package com.shark.demo.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;

/**
 * @author: LiuH
 * @date: 2024/4/23 19:15
 */
public class FtpTest {
    public static void main(String[] args) throws IOException {
        String ftpHost = "your host";
        int ftpPort = 10021; // 默认FTP端口
        String ftpUsername = "username";
        String ftpPassword = "username@0507";

        FTPClient ftpClient = new FTPClient();
        try {
            // 连接到FTP服务器
            ftpClient.connect(ftpHost, ftpPort);
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                throw new IOException("Failed to connect to FTP server, reply code: " + replyCode);
            }

            // 登录
            boolean loginSuccess = ftpClient.login(ftpUsername, ftpPassword);
            if (!loginSuccess) {
                throw new IOException("Failed to login to FTP server");
            }

            // 设置被动模式（可选，根据服务器要求调整）
            ftpClient.enterLocalPassiveMode();

            //
            downloadFile(ftpClient, "/data/rfftp/dcharger.bin", new File("dcharger.bin"));

            // 断开连接
            ftpClient.logout();
        } finally {
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        }
    }

    private static void uploadFile(FTPClient ftpClient, InputStream inputStream, String remoteFilePath) throws IOException {
        if (ftpClient.storeFile(remoteFilePath, inputStream)) {
            System.out.println("File uploaded successfully.");
        } else {
            System.out.println("Failed to upload file.");
        }
    }

    private static void downloadFile(FTPClient ftpClient, String remoteFilePath, File localFile) throws IOException {
        try (OutputStream outputStream = new FileOutputStream(localFile)) {
            if (ftpClient.retrieveFile(remoteFilePath, outputStream)) {
                System.out.println("File downloaded successfully.");
            } else {
                System.out.println("Failed to download file.");
            }
        }
    }
}
