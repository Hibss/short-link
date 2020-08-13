package com.syz.eurekaserver.utils;

import java.security.MessageDigest;

public class ShortUrlMD5Util {
    private static final String KEY = "MD5TEST";

    public static void main(String[] args) {
        String sLongUrl = "http://www.baidu.com/bbs/bba/bbb/asd?asdasd=12312&assdasd=12312&asdasd=asdajshd";
        System.out.println("长链接:"+sLongUrl);
        String aResult = shortUrl(sLongUrl);
        System.out.println(aResult);
    }

    public static String shortUrl(String url) {
        // 要使用生成 URL 的字符
        String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z"

        };
        // 对传入网址进行 MD5 加密
        String hex = md5ByHex(KEY + url);
        System.out.println(hex);

        String sTempSubString = hex.substring(8, 16);
        long lHexLong = 0x3FFFFFF3 & Long.parseLong (sTempSubString, 16);
        String outChars = "" ;
        for ( int j = 0; j < 6; j++) {
            long index = 0x0000003C & lHexLong;
            outChars += chars[( int ) index];
            // 每次循环按位右移 5 位
            lHexLong = lHexLong >> 5;
        }
        return outChars;
    }
    /**
     * MD5加密(32位大写)
     * @param src
     * @return
     */
    public static String md5ByHex(String src) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] b = src.getBytes();
            md.reset();
            md.update(b);
            byte[] hash = md.digest();
            String hs = "";
            String stmp = "";
            for (int i = 0; i < hash.length; i++) {
                stmp = Integer.toHexString(hash[i] & 0xFF);
                if (stmp.length() == 1)
                    hs = hs + "0" + stmp;
                else {
                    hs = hs + stmp;
                }
            }
            return hs.toUpperCase();
        } catch (Exception e) {
            return "";
        }
    }
}
