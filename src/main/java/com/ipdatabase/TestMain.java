package com.ipdatabase;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/12/6.
 */
public class TestMain {

    public static void main(String[] args) throws IOException {
        String IP = "119.75.217.56";
        String dataFile = Thread.currentThread().getContextClassLoader().getResource("qqwry.dat").getPath();
        try {
            IPSeeker.I.init(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String country = IPSeeker.I.getAddress(IP);
        System.out.println(country);
    }


}
