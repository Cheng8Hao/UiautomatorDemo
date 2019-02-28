package com.example.myapplication2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ShutdownReceiver extends BroadcastReceiver {
    private static final String TAG = "ch6 ShutdownReceiver";
    private static final String ACTION_SHUTDOWN = "android.intent.action.ACTION_SHUTDOWN";
    private static final String FILE_NAME = "/uiautomatorTest.txt";
    private static int startTime;
    private static int endTime;
    private static String testTimeString;
    private static String Result;
    private static int standardtest_time;//小时

    @Override
    public void onReceive(Context context, Intent intent) {  //即将关机时，要做的事情
        if (intent.getAction().equals(ACTION_SHUTDOWN)) {
            Log.d(TAG, "onReceive: ");
            endTime = (int) System.currentTimeMillis();
            try {
                startTime = Settings.System.getInt(context.getContentResolver(), "uiauto_startTime");
                standardtest_time=Settings.System.getInt(context.getContentResolver(), "standardtest_time");
            } catch (Settings.SettingNotFoundException e) {
                Log.d(TAG, "onReceive: Settings.SettingNotFoundException e");
                e.printStackTrace();
            }
            Log.d(TAG, "onReceive: startTime=" + startTime);
            Log.d(TAG, "onReceive: endTime=" + endTime);
            int timegap = Math.abs(endTime - startTime) / 1000;//秒
            testTimeString = getTimeString(timegap);//
            Log.d(TAG, "onReceive: timegap=" + timegap + "standardtest_time=" + standardtest_time);
            Result = ((timegap / 60 / 60) > standardtest_time ? "pass" : "fail");
            try {
                //write("testcaseNo:" + "last" + "\n" + "endtime" + getTimeInfo() + "\n" + getBatterryInfo() + "\n");
                write("\n"
                        + "11" + "\t" + "Standby until shutdown" + "\t" + getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\n"
                        + "TestTime" + "\t" + testTimeString + "\n"
                        + "Result" + "\t" + Result + "\n"
                        + "SoftWare" + "\t" + getproductversion() + "\n");
            } catch (IOException e) {
                Log.d(TAG, "write IOException: ");
                e.printStackTrace();
            }
        }
    }
    public String getBatterryVoltage() throws IOException {
        Log.d(TAG, "getBatterryInfo: ");
        String batteryinfo = null;
        CMDUtils.CMD_Result rs = CMDUtils.runCMD("dumpsys battery", true, true);
        batteryinfo = rs.success;
        Log.d(TAG, "getBatterryInfo: " + batteryinfo);
        int level = batteryinfo.indexOf("level");
        int scale = batteryinfo.indexOf("scale");
        int voltage = batteryinfo.lastIndexOf("voltage");
        int temperature = batteryinfo.indexOf("temperature");
        String btlevel = batteryinfo.substring(level, scale).trim();
        String btvoltage = batteryinfo.substring(voltage, temperature).trim();
        Log.d(TAG, "btlevel=" + btlevel + " " + "btvoltage=" + btvoltage);//btlevel=level:100 btvoltage=voltage:4360
        String voltage1 = btvoltage.substring(8);
        batteryinfo = btvoltage + "mV";
        return voltage1;
    }

    public String getBatterryElectric() throws IOException {
        Log.d(TAG, "getBatterryInfo: ");
        String batteryinfo = null;
        CMDUtils.CMD_Result rs = CMDUtils.runCMD("dumpsys battery", true, true);
        batteryinfo = rs.success;
        Log.d(TAG, "getBatterryInfo: " + batteryinfo);
        int level = batteryinfo.indexOf("level");
        int scale = batteryinfo.indexOf("scale");
        int voltage = batteryinfo.lastIndexOf("voltage");
        int temperature = batteryinfo.indexOf("temperature");
        String btlevel = batteryinfo.substring(level, scale).trim();
        String btvoltage = batteryinfo.substring(voltage, temperature).trim();
        Log.d(TAG, "btlevel=" + btlevel + " " + "btvoltage=" + btvoltage);//btlevel=level:100 btvoltage=voltage:4360
        batteryinfo = btlevel + "%";
        String electric = btlevel.substring(6);
        return electric;
    }
    public String getTimeInfo() {
        SimpleDateFormat format = new SimpleDateFormat("y-M-d  H:m:s");
        String time = format.format(Calendar.getInstance().getTime());
        Log.d(TAG, "完整的时间和日期： " + time);
        String testtime = time;
        return testtime;
    }
    public String getproductversion() throws IOException {
        Log.d(TAG, "getproductversion: ");
        String getproductversion = null;
        CMDUtils.CMD_Result rs = CMDUtils.runCMD("getprop ro.mediatek.version.release", true, true);
        getproductversion = rs.success;
        Log.d(TAG, "getproductversion: getproductversion=" + getproductversion);
        return getproductversion;
    }

    public String getTimeString(int seconds) {
        int T = seconds;
        int D = T / 60 / 60 / 24;
        int H = T / 60 / 60 % 24;
        int M = T / 60 % 60;
        int S = T % 60;
        String time = "";
        if (D > 0) {
            time = D + "d" + H + "h" + M + "m" + S + "s";
            return time;
        } else if (H > 0) {
            time = H + "h" + M + "m" + S + "s";
            return time;
        } else if (M > 0) {
            time = M + "m" + S + "s";
            return time;
        } else if (S > 0) {
            time = S + "s";
            return time;
        }
        return time;
    }

    public void write(String content) throws IOException {
        // 如果手机插入了SD卡，而且应用程序具有访问SD的权限
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.d(TAG, "具有访问SD的权限: ");
            // 获取SD卡的目录
            File sdCardDir = Environment.getExternalStorageDirectory();
            Log.d(TAG, "sdCardDir.getCanonicalPath(): " + sdCardDir.getCanonicalPath());
            File targetFile = new File(sdCardDir.getCanonicalPath() + FILE_NAME);
            // 以指定文件创建 RandomAccessFile对象
            RandomAccessFile raf = new RandomAccessFile(targetFile, "rw");
            // 将文件记录指针移动到最后
            raf.seek(targetFile.length());
            // 输出文件内容
            raf.write(content.getBytes());
            // 关闭RandomAccessFile
            raf.close();
            Log.d(TAG, "raf.close()");
        }
    }
}