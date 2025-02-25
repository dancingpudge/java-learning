/*
package demo.file;

import com.alibaba.fastjson.JSON;

*/
/**
 * @description: TODO
 * @author: LiuH
 * @date: 2024/4/17 16:20
 *//*

public class FileUpdate {
    */
/**

     * 处理升级过程

     *

     * @param mac 地磁序列号

     * @param ge 地磁上传信息

     *//*


    public byte[] process_upgrade(String mac, GeoBase ge, FrankBase frank, RequestAddr reqAddr,

                                  DataSource dataSource) {

        if (upgradeFile == null || upgradeFile.header == null) { //升级文件没有正常加载

            logger.info(String.format("地磁 %s 升级，升级文件加载不正常，重新加载", mac));

            return null;

        }

        logger.info("处理升级过程开始...进度为："+State);

        //根据地磁升级的进度，发送不同的数据

        if (State == UpgradeState.usWaitReboot) //等待地磁上传，发回重启命令

        {

            logger.info("等待地磁上传...发回重启命令");

            byte[] pdata = geo_config.get_reboot_data(mac);

            logger.info(String.format("地磁 %s 升级，发送重启命令", mac));

            State = UpgradeState.usWait;

            lastAccess = new Date();

            return pdata;

        } else if (State == UpgradeState.usWait) //等待地磁上传，发回切换下载状态命令

        {

            logger.info("等待请求文件头");

            boolean hlperr = false; //是否可恢复的求救故障。如是，直接升级app

            if (ge instanceof GeoHelp) {

                GeoHelp hlp = (GeoHelp) ge;

                hlperr = (hlp.isLdrError() || hlp.isAppError()); //可恢复的求救故障，可以直接升级APP

            }

            boolean geopark = false;

            if (ge instanceof GeoPark) {

                geopark = true; //小心跳上传，肯定是在app模式

            }

            if ((ge.boot || hlperr) && upgradeFile.getAppType() == UpgradeFileInfo.AppType.APP) {

                //当前在boot模式存活包，要升级app。可以继续操作

                byte[] data = geo_config.get_next_step(mac, geo_config.NXT_ACT_DWN);

                logger.info(String.format("地磁 %s 升级，boot状态下升级app，切换到下载模式", mac));

                State = UpgradeState.usWaitAck;

                lastAccess = new Date();

                return data;

            } else if (upgradeFile.getAppType() == UpgradeFileInfo.AppType.BOOT) {

                if (geopark) {

                    //当前在app模式存活包，要升级boot。可以继续操作

                    byte[] data = geo_config.get_next_step(mac, geo_config.NXT_ACT_DWN);

                    logger.info(String.format("地磁 %s 升级，app状态下升级boot，切换到下载模式", mac));

                    State = UpgradeState.usWaitAck;

                    lastAccess = new Date();

                    return data;

                } else {

                    logger.info("求救是在app下，不能升级boot");

                    return null;

                }

            } else {

                byte[] pdata = geo_config.get_reboot_data(mac);

                logger.info(String.format("地磁 %s 升级，工作模式不明确，发送重启命令", mac));

                State = UpgradeState.usWait;

                lastAccess = new Date();

                return pdata;

            }

        } else if (State == UpgradeState.usWaitAck) //等待地磁升级请求

        {

            logger.info("等待地磁升级请求....");

            if ((ge != null && ge.cmd == geo_config.CMD_LDR_SND)

                    || (frank != null && (frank.packagetype == FrankUtil.CMD_FRANK_CPU1_ASK_HEADER

                    || frank.packagetype == FrankUtil.CMD_FRANK_CPU2_ASK_HEADER

                    || frank.packagetype == FrankUtil.CMD_FRANK_NBFW_ASK_HEADER))) //固件信息请求

            {

                //发送升级头信息

                logger.info("发送升级头文件。。。。。。");

                */
/**

                 * 启动配置参数 CR_TYPEDEF struct { (*000*) int32u addr; (* APP 的地址 *)

                 * (*004*) int32u size; (* APP 的长度 *) (*008*) int16u hash; (*

                 * APP 的校验 *) (*010*) int16u crc16; (* 结构体校验 *) (*012*) }

                 * sGEO_LDR;

                 *//*


                byte[] data = null;

                String args = OceanUtils.getHexString(upgradeFile.header);

                logger.info(String.format("地磁 upgradeFile.header %s", args));

                logger.info(String.format("地磁 upgradeFile.header %s,%s", JSON.toJSONString(frank),

                        mac));

                if (frank == null) {

                    data = geo_config.pack_geo_head((byte) 0xBB, mac, geo_config.CMD_LDR_SND,

                            upgradeFile.header);

                } else {

                    data = FrankUtil.pack_geo_head(frank.pid, mac, frank, upgradeFile.header);

                }

                logger.info(String.format("地磁 %s 升级，发送文件头信息：版本号%s, 启动地址0x%08X, 文件长度%d", mac,

                        upgradeFile.ver, upgradeFile.addr, upgradeFile.filesize));

                State = UpgradeState.usData;

                lastAccess = new Date();

                return data;

            } else {

                byte[] data = geo_config.get_next_step(mac, geo_config.NXT_ACT_DWN);

                logger.info(String.format("地磁 %s 升级，再次切换到下载模式", mac));

                State = UpgradeState.usWaitAck;

                lastAccess = new Date();

                return data;

            }

        } else if (State == UpgradeState.usData) //数据内容发送

        {

            logger.info("数据内容发送......");

            if ((ge != null && ge.cmd == geo_config.CMD_DAT_REQ && ge.len == 14)

                    || (frank != null && (frank.packagetype == FrankUtil.CMD_FRANK_CPU1_ASK_DATA

                    || frank.packagetype == FrankUtil.CMD_FRANK_CPU2_ASK_DATA

                    || frank.packagetype == FrankUtil.CMD_FRANK_NBFW_ASK_DATA))) //请求新数据

            {

                lastAccess = new Date();

                float progress = 0;

                logger.info(String.format("地磁 %s 升级，请求升级数据地址: 0x%08X", mac, reqAddr.addr));

                if (reqAddr.addr < upgradeFile.addr

                        || reqAddr.addr > upgradeFile.addr + upgradeFile.filesize) {

                    logger.error(String.format("地磁 %s 升级，请求地磁范围错误，不下发数据", mac));

                    return null; //地址超过范围(低于基址或超过文件末尾)，不能回应

                }

                int offset = (int) (reqAddr.addr - upgradeFile.addr); //计算数据偏移量

                int dataSize = 0; //当前要发送的数据长度

                int packageLength = geo_config.DN_PKGSIZE;

                switch (dataSource) {

                    case dsMobileOneNET:

                        packageLength = geo_config.ONENET_DATA_PKGSIZE; //移动onenet地磁每包升级可以发384字节

                        break;

                    case dsMobileOneNETFrank:

                        packageLength = geo_config.ONENET_DATA_PKGSIZE_FRANK; //384字节

                        break;

                    case dsTelecomOceanConnectFrank:

                        packageLength = geo_config.ONENET_DATA_PKGSIZE_FRANK; //384

                        break;

                    case dsTelecomCTWingFrank:

                        packageLength = geo_config.ONENET_DATA_PKGSIZE_FRANK; //384

                        break;

                    default: //联通目前走默认大小

                        packageLength = geo_config.DN_PKGSIZE;

                        break;

                }



                if (offset + packageLength < upgradeFile.filesize) //还不是最后一段数据

                {

                    dataSize = packageLength;

                } else {

                    dataSize = (int) (upgradeFile.filesize - offset); //最后一包数据，可能不够一整包长

                }

                progress = (int) ((float) offset / upgradeFile.filesize * 100.0);

                byte[] filedata = new byte[dataSize + 4]; //返回数据增加数据地址，便于接收端区分

                System.arraycopy(reqAddr.bAddr, 0, filedata, 0, 4); //请求里的数据地址原样返回

                //FIXME: 按配置项设置，可选择读文件还是读内存 直接内存读。防止内存被误改，改从只读文件里读。

                //停用

                System.arraycopy(upgradeFile.data, offset, filedata, 4, dataSize); //前4字节是数据地址

                */
/*

                 * byte[] bindata =

                 * fileHelper.readFileBinary(upgradeFile.filename, offset,

                 * dataSize); if (bindata == null) { logger.info(String.format(

                 * "地磁 %s 升级，读升级文件异常，数据地址: 0x%08X，进度 %.2f", mac, req.addr,

                 * progress)); return null; } System.arraycopy(bindata, 0,

                 * filedata, 4, dataSize); //前4字节是数据地址

                 *//*


                byte[] data = null;

                if (frank == null) {

                    data = geo_config.pack_geo_head((byte) 0xBB, mac, geo_config.CMD_DAT_REQ,

                            filedata);

                } else {

                    data = FrankUtil.pack_geo_head(frank.pid, mac, frank, filedata);

                }



                //查找地磁所在的组，并更新进度

                if (NB_IoT.getMac_cellid().containsKey(mac)) {

                    String cellId = NB_IoT.getMac_cellid().get(mac);

                    ConcurrentHashMap<String, UpgradeProgress> connections = NB_IoT.getUpgrade_connections()

                            .get(cellId);

                    if (connections != null) {

                        UpgradeProgress up = connections.get(mac);

                        up.setProgress(progress);

                        connections.put(mac, up);

                    }

                }

                logger.info(String.format("地磁 %s 升级，下发升级数据包，进度 %.2f", mac, progress));

                return data;

            } else if ((ge != null && ge.cmd == geo_config.CMD_LDR_SND && ge.len == 10)

                    || (frank != null && frank.packagetype == FrankUtil.CMD_FRANK_CPU1_ASK_HEADER)) // 请求下载文件头信息

            {

                byte[] data = null;

                if (frank == null) {

                    data = geo_config.pack_geo_head((byte) 0xBB, mac, geo_config.CMD_LDR_SND,

                            upgradeFile.header);

                } else {

                    data = FrankUtil.pack_geo_head(frank.pid, mac, frank, upgradeFile.header);

                }

                logger.info(String.format("地磁 %s 升级，发送头信息：版本号%s, 启动地址0x%08X, 文件长度%d", mac,

                        upgradeFile.ver, upgradeFile.addr, upgradeFile.filesize));

                return data;

            } else {

                logger.error(String.format("地磁 %s 升级，状态不明确，不能处理1(上传协议不匹配)，重新切换下载模式", mac));

                State = UpgradeState.usWaitAck; //回到切换模式

                return null;

            }

        } else {

            logger.error(String.format("地磁 %s 升级，状态不明确，不能处理2(状态转换)", mac));

            return null;

        }

    }
}
*/
