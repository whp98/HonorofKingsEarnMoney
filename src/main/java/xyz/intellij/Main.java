package xyz.intellij;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import se.vidstige.jadb.JadbConnection;
import se.vidstige.jadb.JadbDevice;
import se.vidstige.jadb.JadbException;

import xyz.intellij.util.*;
import java.io.IOException;
import java.util.List;

/**
 * 一个简单的王者荣耀金币刷取软件
 * 本软件的设计思想如下：
 * 这个主要是解放肝帝的双手，使用程序来完成重复的劳动来获取金币
 * 本程序设计思想是代替双手所以并不是外挂，本程序效果和双手操作的结果相同。
 *
 */
public class Main {

    public static void main(String[] args) {
	// write your code here
        boolean boolRun = true;
        JadbConnection jadb = new JadbConnection();
        try {
            List<JadbDevice> devices = jadb.getDevices();
            if (devices!=null){
                System.out.println("设备数量【"+devices.size()+"】");
                final JadbDevice device0 = devices.get(0);
//                Point a = new Point();
//                a.setX(444.0);
//                a.setY(555.0);
//                tap(device0,a);
//                sleep(1000L);
//                tap(device0,a);
//                tap(device0,a);
                String config = FileUtil.ReadFile("src/main/java/xyz/intellij/config1.3.json");
                JSONObject points = JSON.parseObject(config);
                System.out.println(points);
                JSONObject start = points.getJSONObject("start");
                JSONObject again = points.getJSONObject("again");
                Point pstart = JSON.parseObject(start.toString(),xyz.intellij.util.Point.class);
                final Point pagain = JSON.parseObject(again.toString(),xyz.intellij.util.Point.class);
                System.out.println(pagain);
                System.out.println(pstart);
                Thread thread = new Thread(new Runnable() {
                    public void run() {
                        //不断点击补刀（或者说是重新开始）
                        try {
                            while(true){
                                sleep(100L);
                                tap(device0,pagain);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JadbException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
                int x = 0;
                while(x<100){
                    //点击开始游戏
                    tap(device0,pstart);
                    sleep(3000L);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JadbException e) {
            e.printStackTrace();
        }
    }

    private static void tap(JadbDevice jadbDevice, Point point) throws IOException, JadbException {
        if (jadbDevice!=null&&point!=null){
            jadbDevice.executeShell("input tap "+point.getX()+" "+point.getY());
            System.out.println("点击"+point);
        }else {
            System.out.println("设备或者点为空");
        }
    }

    private static void sleep(Long millis){
        try {
            System.out.println("暂停"+millis+"ms ...");
            Thread.sleep(millis);
        }catch (Exception e){

        }
    }
}
