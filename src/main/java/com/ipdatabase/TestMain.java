package com.ipdatabase;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/12/6.
 */
public class TestMain {

    public static void main(String[] args){
        String IP = "119.75.217.56";
        String dataFile = Thread.currentThread().getContextClassLoader().getResource("qqwry.dat").getPath();
        try {
            IPSeeker.I.init(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String country = IPSeeker.I.getAddress(IP);
        System.out.println(country);

        test();
    }

    public static void test(){
        // 按指定模式在字符串查找
        String line = "北京市123北京百度网讯科技有限公司BGP节点";
        String pattern = "(\\.+12)(\\.*市)(.*区)";

        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(line);
        if (m.find( )) {
            System.out.println("Found value: " + m.group(0) );
            System.out.println("Found value: " + m.group(1) );
            System.out.println("Found value: " + m.group(2) );
            System.out.println("Found value: " + m.group(3) );
        } else {
            System.out.println("NO MATCH");
        }
    }
}
