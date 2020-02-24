package xyz.intellij;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import se.vidstige.jadb.JadbConnection;
import se.vidstige.jadb.JadbDevice;
import se.vidstige.jadb.JadbException;
import xyz.intellij.util.FileUtil;
import xyz.intellij.util.Point;

import java.io.IOException;
import java.util.List;

import static java.lang.Thread.sleep;

public class WeiShiShua {
    public static void main(String[] args) {
        JadbConnection jadb = new JadbConnection();
        try {
            List<JadbDevice> devices = jadb.getDevices();
            if (devices!=null){
                System.out.println("设备数量【"+devices.size()+"】");
                final JadbDevice device0 = devices.get(0);
                String config = FileUtil.ReadFile("src/main/java/xyz/intellij/configweishi.json");
                JSONObject points = JSON.parseObject(config);
                System.out.println(points);
                JSONObject start = points.getJSONObject("a");
                JSONObject again = points.getJSONObject("b");
                Point pstart = JSON.parseObject(start.toString(),xyz.intellij.util.Point.class);
                final Point pagain = JSON.parseObject(again.toString(),xyz.intellij.util.Point.class);
                System.out.println(pagain);
                System.out.println(pstart);
                int i=1;
                while (i<1000){
                    System.out.println("次数："+i);
                    swip(device0,pstart,pagain);
                    int max=8000,min=1000;
                    long ran2 = (long) (Math.random()*(max-min)+min);
                    sleep(5000L+ran2);
                    i++;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JadbException e) {
            e.printStackTrace();
        }
    }
    public static void swip(JadbDevice jadbDevice, Point pa,Point pb){
        if (jadbDevice!=null&&pa!=null&&pb!=null){
            try {
                jadbDevice.executeShell("input swipe "+pa.getX()+" "+pa.getY()+" "+pb.getX()+" "+pb.getY());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JadbException e) {
                e.printStackTrace();
            }
            System.out.println("滑动"+pa+"->>>"+pb);
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
