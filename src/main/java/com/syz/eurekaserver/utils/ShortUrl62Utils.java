package com.syz.eurekaserver.utils;

import org.apache.commons.lang3.StringUtils;

public class ShortUrl62Utils {
    private final static String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    /**
     * 将数字转为62进制
     * @param num    Long 型数字
     * @return 62进制字符串
     */

    public static String base62Encode(long num) {
        StringBuilder sb = new StringBuilder();
        int remainder;
        int scale = 62;
        while (num > scale - 1) {
            remainder = Long.valueOf(num % scale).intValue();
            sb.append(chars.charAt(remainder));
            num = num / scale;
        }
        sb.append(chars.charAt(Long.valueOf(num).intValue()));
        String value = sb.reverse().toString();
        return StringUtils.leftPad(value, 6, '0');
    }

    /**
     * 62进制字符串转为数字
     * @param str 编码后的62进制字符串
     * @return 解码后的 10 进制字符串
     */
    public static long base62Decode(String str) {
        int scale = 62;
        str = str.replace("^0*", "");
        long num = 0;
        int index;
        for (int i = 0; i < str.length(); i++) {
            index = chars.indexOf(str.charAt(i));
            num += (long) (index * (Math.pow(scale, str.length() - i - 1)));
        }
        return num;
    }

    public static void main(String[] args) {
        String uri = "link/cityList";
        System.out.println(ShortUrl62Utils.base62Decode(uri));
    }
}
