package demo.io;


import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.ftp.Ftp;

import java.io.IOException;

public class NewIO {

    public static void main(String[] args) throws IOException {
        //匿名登录（无需帐号密码的FTP服务器）
        Ftp ftp = new Ftp("");
        //进入远程目录
        ftp.cd("/opt/upload");
        //上传本地文件
        ftp.upload("/opt/upload", FileUtil.file("e:/test.jpg"));
        //下载远程文件
        ftp.download("/opt/upload", "test.jpg", FileUtil.file("e:/test2.jpg"));
        //关闭连接
        ftp.close();
    }
}
