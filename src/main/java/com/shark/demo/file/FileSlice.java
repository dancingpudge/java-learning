/*
package demo.file;

import java.io.FileInputStream;

*/
/**
 * @description: TODO
 * @author: LiuH
 * @date: 2024/4/17 16:18
 *//*

public class FileSlice {
    */
/**
     * 加载程序文件
     *
     * @param name 文件名称 param name="size">文件长度,字节数</param>
     * @return
     *//*

    public byte[] binLoad(String name) {
        filesize = 0;
        File file = new File(name);
        if (!file.exists())
            return null;
        filesize = (int) file.length(); //文件长度
        // 过滤文件大小
        if (filesize <= 1024 || filesize > 2 * 1024 * 1024) {
            logger.info("升级文件大小不符合要求，被过滤");
            return null;
        }
        FileInputStream f = null;
        DataInputStream br = null;
        // 加载文件
        try {
            f = new FileInputStream(file);
            br = new DataInputStream(f);
            data = new byte[filesize];
            br.readFully(data); //一次性加载全部
        } catch (Exception e) {
            logger.error("升级文件" + filename + "加载失败", e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                }
            }
            if (f != null) {
                try {
                    f.close();
                } catch (Exception e) {
                }
            }
        }
        return data;
    }

    */
/**
     * 组装下发命令数据包（实时返回）
     *//*

    public static byte[] pack_geo_head(int pid, String mac, FrankBase frank, String command) {
        command = command.endsWith("\\r\\n") ? command : command + Constants.IOT_COMMAND_END;
        logger.info("pack_geo_head_pre：mac=" + mac + ",command=" + command);
        command = command.replaceAll("\\\\r\\\\n", "\r\n");
        logger.info("pack_geo_head_after：mac=" + mac + ",command=" + command);
        byte[] data = command.getBytes();
        if (mac.length() == 6) {
            mac = "001D" + mac + "580000"; //lora地磁补齐8字节卡号
        }
        int datalen = data.length + 27;
        byte[] head = new byte[datalen];
        head[0] = 0x02; //包头
        int packetid = pid;
        if (pid < 0) { //如果未传入，则自动生成。传入时，回复设备使用同一个pid
            packetid = getPacketid();
        }
        head[1] = geo_config.ushorthigh((short) packetid);
        head[2] = geo_config.ushortlow((short) packetid);
        for (int i = 0; i < 8; i++) {
            head[3 + i] = (byte) 0xff;
        }
        for (int i = 0; i < 8; i++) {
            head[11 + i] = (byte) Integer.parseInt(mac.substring(i * 2, i * 2 + 2), 16); //java substring(startindex, endindex)
        }
        head[19] = geo_config.ushorthigh((short) datalen); //长度
        head[20] = geo_config.ushortlow((short) datalen);

        head[21] = (byte) frank.protocol; //版本号1
        head[22] = (byte) frank.mode; //工作模式
        head[23] = (byte) 6; //包类型
        head[24] = (byte) getSession();
        if (data.length > 0) {
            System.arraycopy(data, 0, head, 25, data.length);
        }
        int crc = geo_config.app_crc16c(head, 0, head.length - 2);
        head[head.length - 2] = geo_config.ushorthigh(crc);
        head[head.length - 1] = geo_config.ushortlow(crc);

        return head;
    }
}
*/
