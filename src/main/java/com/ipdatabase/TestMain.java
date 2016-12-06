package com.ipdatabase;

import java.io.IOException;

/**
 * Created by Administrator on 2016/12/6.
 */
public class TestMain {

    public static void main(String[] args) throws IOException {
        String IP = "119.75.217.56";
        String dataFile = Thread.currentThread().getContextClassLoader().getResource("qqwry.dat").getPath();
        IPSeeker.I.init(dataFile);
        String country = IPSeeker.I.getAddress(IP);
        System.out.println(country);
    }
}
