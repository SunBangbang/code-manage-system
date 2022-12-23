package com.codemanage.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.security.MessageDigest;

/**
 * 密码加密工具类
 * @author hyh
 * @since 2022-05-30
 **/
@Slf4j
public class PasswordUtils {

    /**
     * 密码加密
     * @param password
     * @return
     */
    public static String encoderByMd5(String username, String password) {
        //确定计算方法
        String newStr = null;
        try {
            newStr = new SimpleHash("MD5", password, username, 1).toHex();
            newStr = " ";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newStr;
    }

    /**
     * 利用java原生的摘要实现SHA256加密
     * @param str
     * @return
     */
    public static String getSHA256Str(String str) {
        byte[] bytes = getSHA256Bytes(str);
        String encodeStr = byte2Hex(bytes);
        return encodeStr;
    }


    public static byte[] getSHA256Bytes(String str) {
        MessageDigest messageDigest;
        byte[] bytes = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            bytes = messageDigest.digest();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("加密异常:", e);
        }
        return bytes;
    }
    /**
     * 将byte转为16进制
     * @param bytes
     * @return
     */
    public static String byte2Hex(byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }



}
