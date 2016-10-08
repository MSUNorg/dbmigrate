/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.dbmigrate.support.utils;

import java.nio.charset.Charset;

import com.lamfire.code.Base64;
import com.lamfire.utils.StringUtils;

/**
 * @author zxc Oct 8, 2016 9:55:41 AM
 */
public class Encrypt {

    public static final String  ENCODE_UTF8  = "UTF-8";
    public static final Charset CHARSET_UTF8 = Charset.forName(ENCODE_UTF8);

    public static String        aeskey       = "dbm.commonap.xxx";

    public static void main(String[] args) throws Exception {
        String str = "nihao";
        String aesstr = AESencodeStr(aeskey, str.getBytes());
        System.out.println(aesstr);
        System.out.println(AESdecode(aeskey, Base64.decode(aesstr)));
    }

    public static String AESencodeStr(String key, byte[] source) throws Exception {
        byte[] encode = AESencode(key, source);

        String encodeStr = Base64.encode(encode);
        if (StringUtils.isEmpty(encodeStr)) throw new RuntimeException(
                                                                       "AESencode bytes is null! or Base64.encode String is null");
        encodeStr = encodeStr.replaceAll("[\\s*\t\n\r]", "");
        return encodeStr;
    }

    public static byte[] AESencode(String key, byte[] source) throws Exception {
        byte[] aeskeyBytes = key.getBytes(CHARSET_UTF8);
        if (aeskeyBytes == null) return null;
        return com.lamfire.code.AES.encode(source, aeskeyBytes);
    }

    public static String AESdecode(byte[] aeskeyBytes, byte[] source) throws Exception {
        if (aeskeyBytes == null) return null;
        byte[] decode = com.lamfire.code.AES.decode(source, aeskeyBytes);
        return new String(decode, CHARSET_UTF8).trim();
    }

    public static String AESdecode(String key, byte[] source) throws Exception {
        byte[] aeskeyBytes = key.getBytes(CHARSET_UTF8);
        if (aeskeyBytes == null) return null;
        byte[] decode = com.lamfire.code.AES.decode(source, aeskeyBytes);
        return new String(decode, CHARSET_UTF8).trim();
    }
}
