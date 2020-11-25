package com.imooc.mall.util;

import com.imooc.mall.common.Constant;
import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 描述：MD5工具
 */
public class MD5Utils {
    public static String getMD5Str(String strValue) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] digest = md5.digest((strValue+ Constant.SALT).getBytes());
        String s = Base64.encodeBase64String(digest);
        return s;
    }

    public static void main(String[] args) {
        try {
            String s=MD5Utils.getMD5Str("HELLO");
            System.out.println(s);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
