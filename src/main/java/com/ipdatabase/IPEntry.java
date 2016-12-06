package com.ipdatabase;

/**
 * Created by Administrator on 2016/12/6.
 */
/**
 * * 一条IP范围记录，不仅包括国家和区域，也包括起始IP和结束IP *
 *
 *
 * @author swallow
 */
public class IPEntry {
    public String beginIp;
    public String endIp;
    public String country;
    public String area;

    /**
     * 构造函数
     */

    public IPEntry() {
        beginIp = endIp = country = area = "";
    }

    public String toString() {
        return this.area + "  " + this.country + "IP范围:" + this.beginIp + "-"
                + this.endIp;
    }
}
