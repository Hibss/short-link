package com.syz.eurekaserver.filter;

import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

public class test
{
    public static void main(String[] args) throws FileNotFoundException {


        String path = System.getProperty("user.dir");
        System.out.println("user.dir " + path);

        //获取classes目录绝对路径

         path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        System.out.println("getResource " + path);


         path = ResourceUtils.getURL("classpath:").getPath();
        System.out.println("getURL(\"classpath:\") " + path);

    }
}
