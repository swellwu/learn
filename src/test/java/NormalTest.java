import com.ipdatabase.IPSeeker;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/12/8.
 */
public class NormalTest {

    private String formatArea(String address){
        String result="中国";
        String pattern1 = "(\\D+省)(\\D+市)";
        // 创建 Pattern 对象
        Pattern r1 = Pattern.compile(pattern1);
        // 现在创建 matcher 对象
        Matcher m1 = r1.matcher(address);
        if (m1.find()) {
            return m1.group(2);
        }
        String pattern2= "(\\D+市)";
        Pattern r2 = Pattern.compile(pattern2);

        Matcher m2=r2.matcher(address);
        if(m2.find()){
            return m2.group(1);
        }
        String pattern3="(\\D+省)";
        Pattern r3=Pattern.compile(pattern3);
        Matcher m3=r3.matcher(address);
        if(m3.find()) {
            return m3.group(1);
        }
        if(address.indexOf("内蒙古")!=-1){
            return "内蒙古自治区";
        }
        return result;
    }

    @Test
    public void addressFormatTest() throws IOException {
        String dataFile = Thread.currentThread().getContextClassLoader().getResource("qqwry.dat").getPath();
        try {
            IPSeeker.I.init(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int count=1;
        int valid=1;
        List<String> ipList=IPSeeker.I.getAllIp();
        System.out.println(ipList.size());
        List<String> invalidAddressList=new LinkedList<>();
        for(String ip:ipList){
            try {
                String address = IPSeeker.I.getAddress(ip);
                if(!formatArea(address).equals("中国")){
                    valid++;
                } else{
//                    invalidAddressList.add(address);
                }
                count++;
            }catch (Exception e){
            }
        }
        System.out.println("valid rate="+((double)valid/count));
//        System.out.println("valid address:");
//        for(String address:invalidAddressList){
//            System.out.println(address);
//        }
    }

    @Test
    public void getAddressTest(){
        String dataFile = Thread.currentThread().getContextClassLoader().getResource("qqwry.dat").getPath();
        try {
            IPSeeker.I.init(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String ip="62.76.154.0";
        System.out.println(IPSeeker.I.getAddress(ip));
    }
}
