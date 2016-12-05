package com.mail;

import java.util.Date;

public class MailTest {
    public static void main(String[] args) {

    	MessageVo mv = new MessageVo();
    	
    	mv.setSubject("需要更  改的地方");
    	
    	mv.setText("周一上班后请第一时间更新枪王的包，解决上周发现的公鼎支付appid错误导致无法计费的严重问题。同时，要一起更新掉斯凯的客户电话（包括首页客服电话和计费界面上的电话）。\n" +
				"\n" +
				"完了我们再理一理出错的原因还有以后如何规避这样的错误。\n" +
				"\n" +
				"1）批量包，用上周的包名：com.coolsa.xinban.zh\n" +
				"签名、类名、版本号，例行更新。\n" +
				"\n" +
				"2）渠道包：阿里渠道包（142），小辣椒渠道包（079），酷狗渠道号（065），酷我渠道包（111），重新出这四个渠道包。\n" +
				"包名分别是这几个渠道包上周出的包名，升级签名、类名、版本号。");
    	
    	mv.setToMailAddress("18795550577@163.com");
    	
    	SendMail.sendMail(mv);
    }
}
