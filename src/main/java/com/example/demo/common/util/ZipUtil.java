package com.example.demo.common.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.CharEncoding;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 数据压缩和解压缩
 *
 * @author wujl2
 * @date 2022-01-06 15:18
 */
@Slf4j
public class ZipUtil {

    /**
     * 压缩GZip
     *
     * @return String
     */
    public static String gZip(String input) {
        byte[] bytes = null;
        GZIPOutputStream gzip = null;
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            gzip = new GZIPOutputStream(bos);
            gzip.write(input.getBytes(CharEncoding.UTF_8));
            gzip.finish();
            gzip.close();
            bytes = bos.toByteArray();
            bos.close();
        } catch (Exception e) {
            log.error("压缩出错：", e);
        } finally {
            try {
                if (gzip != null)
                    gzip.close();
                if (bos != null)
                    bos.close();
            } catch (final IOException ioe) {
                log.error("压缩出错：", ioe);
            }
        }
        return Base64.encodeBase64String(bytes);
    }

    /**
     * 解压GZip
     *
     * @return String
     */
    public static String unGZip(String input) {
        byte[] bytes;
        String out = input;
        GZIPInputStream gzip = null;
        ByteArrayInputStream bis;
        ByteArrayOutputStream bos = null;
        try {
            bis = new ByteArrayInputStream(Base64.decodeBase64(input));
            gzip = new GZIPInputStream(bis);
            byte[] buf = new byte[1024];
            int num;
            bos = new ByteArrayOutputStream();
            while ((num = gzip.read(buf, 0, buf.length)) != -1) {
                bos.write(buf, 0, num);
            }
            bytes = bos.toByteArray();
            out = new String(bytes, CharEncoding.UTF_8);
            gzip.close();
            bis.close();
            bos.flush();
            bos.close();
        } catch (Exception e) {
            log.error("解压出错：", e);
        } finally {
            try {
                if (gzip != null)
                    gzip.close();
                if (bos != null)
                    bos.close();
            } catch (final IOException ioe) {
                log.error("解压出错：", ioe);
            }
        }
        return out;
    }

    /*public static void main(String[] args) {
        List<AdmdvsInfoDO> aa = new ArrayList<>();
        AdmdvsInfoDO admdvsInfoDO = new AdmdvsInfoDO();
        admdvsInfoDO.setAdmdvs("11");
        admdvsInfoDO.setAdmdvsName("aa");
        aa.add(admdvsInfoDO);
        String s = gZip(JSON.toJSONString(aa));
        String s1 = unGZip(s);
        System.out.println("aa=" + JSON.toJSONString(aa));
        //转集合
        List<AdmdvsInfoDO> quaShrMcsSinDO = JSONObject.parseArray(s1, AdmdvsInfoDO.class);
        //QuaShrMcsDO quaShrMcsDO = JSONObject.parseObject(quaShrMcsFulDO.getBizData(), QuaShrMcsDO.class);
        //转单个对象
        System.out.println("ee=" + JSON.toJSONString(quaShrMcsSinDO));
    }*/
}
