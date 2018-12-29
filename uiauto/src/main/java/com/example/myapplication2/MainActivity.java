package com.example.myapplication2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "myMainActivity";
    private static final String FILE_NAME = "/uiautomatorTest.txt";
    private static int startTime;
    private static int endTime;
    private static String testTimeString;
    private static String Result;
    private static int standardtest_time;//小时
    private static final String configFILE_NAME = "/myconfig.xml";
    Button runBtn;
    /*, getsp;
    SharedPreferences autosp;
    SharedPreferences.Editor editor;*/

    public class ShutdownBroadcastReceiver extends BroadcastReceiver {
        private static final String TAG = "BroadcastReceiver";
        private static final String ACTION_SHUTDOWN = "android.intent.action.ACTION_SHUTDOWN";

        @Override
        public void onReceive(Context context, Intent intent) {  //即将关机时，要做的事情
            if (intent.getAction().equals(ACTION_SHUTDOWN)) {
                Log.d(TAG, "shutonReceive: ");
                endTime = (int) System.currentTimeMillis();
                Log.d(TAG, "onReceive: startTime=" + startTime);
                Log.d(TAG, "onReceive: endTime=" + endTime);
                int timegap = Math.abs(endTime - startTime)/1000;//秒
                testTimeString = getTimeString(timegap);//
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runBtn = (Button) findViewById(R.id.runBtn);
        /*getsp = (Button) findViewById(R.id.getsp);
        autosp = getSharedPreferences("autosp", MODE_PRIVATE);
        editor = autosp.edit();*/
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.ACTION_SHUTDOWN");
        filter.addCategory("android.intent.category.HOME");
        registerReceiver(new ShutdownBroadcastReceiver(), filter);
    }

    /**
     * 点击按钮对应的方法
     *
     * @param v
     */
    public void runMyUiautomator(View v) {
        Log.i(TAG, "runMyUiautomator: ");
        startTime = (int) System.currentTimeMillis();
        Log.d(TAG, "runMyUiautomator: startTime==" + startTime);
        Toast.makeText(MainActivity.this, "test start... ", Toast.LENGTH_SHORT).show();
        new UiautomatorThread().start();
    }

/*    public void getsp(View v) {
        Log.i(TAG, "getsp: ");
        String result1 = autosp.getString("result1", null);
        String result2 = autosp.getString("result2", null);
        Toast.makeText(MainActivity.this, result1 + result2, Toast.LENGTH_LONG).show();
    }*/

    /**
     * 运行uiautomator是个费时的操作，不应该放在主线程，因此另起一个线程运行
     */
    class UiautomatorThread extends Thread {
        @Override
        public void run() {
            super.run();
            // String command = generateCommand("com.example.mytestcast", "TestOne", "demo");
            String command = generateCommand("com.example.mytestcast", "Mytest");

            CMDUtils.CMD_Result rs = CMDUtils.runCMD(command, true, true);
            //rs 是命令执行返回的Log
            Log.d(TAG, "run: " + rs.error + "-------" + rs.success);
/*
            String getproductversion = generateCommand("getprop ro.mediatek.version.release");
            CMDUtils.CMD_Result rs2 = CMDUtils.runCMD(getproductversion, true, true);
            productVersion=rs2.success;
            Log.d(TAG, "run: productVersion="+productVersion);*/
          /*  editor.putString("reslut1", rs.error);
            editor.putString("reslut2", rs.success);
            editor.commit();*/
        }

        /**
         * 生成命令
         *
         * @param pkgName 包名
         * @param clsName 类名
         * @return
         */
        private String generateCommand(String pkgName, String clsName) {
            String command = "am instrument  --user 0 -w -r   -e debug false -e class " + pkgName + "." + clsName + " " + pkgName + ".test/android.support.test.runner.AndroidJUnitRunner";
            Log.e("test1: ", command);
            return command;
        }

        /**
         * 生成命令
         *
         * @param pkgName 包名
         * @param clsName 类名
         * @param mtdName 方法名
         * @return
         */
        public String generateCommand(String pkgName, String clsName, String mtdName) {
            String command = "am instrument  --user 0 -w -r   -e debug false -e class " + pkgName + "." + clsName + "#" + mtdName + " " + pkgName + ".test/android.support.test.runner.AndroidJUnitRunner";
            Log.e("test3: ", command);
            return command;
        }
    }

    /*
    * 获取电池信息  格式：level:100voltage:4232
    */
    public String getBatterryInfo() throws IOException {
        Log.d(TAG, "getBatterryInfo: ");
        String batteryinfo = null;
        CMDUtils.CMD_Result rs = CMDUtils.runCMD("dumpsys battery", true, true);
        batteryinfo = rs.success;
        Log.d(TAG, "getBatterryInfo: " + batteryinfo);
        int level = batteryinfo.indexOf("level");
        int voltage = batteryinfo.lastIndexOf("voltage");
        String btlevel = batteryinfo.substring(level, level + 10).replace(" ", "");
        String btvoltage = batteryinfo.substring(voltage, voltage + 13).replace(" ", "");
        Log.d(TAG, "btlevel=" + btlevel + " " + "btvoltage=" + btvoltage);//btlevel=level:100 btvoltage=voltage:4360
        batteryinfo = btlevel + "\n" + btvoltage + "\n";
        return batteryinfo;
    }


    /*
     * 获取日期信息 格式：//y-M-d h:m:s
     */
    public String getTimeInfo() {
        SimpleDateFormat format = new SimpleDateFormat("y-M-d  H:m:s");
        String time = format.format(Calendar.getInstance().getTime());
        Log.d(TAG, "完整的时间和日期： " + time);
        String testtime = time;
        return testtime;
    }

    private void write(String content) throws IOException {
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

    //获取myconfig.xml文件
    public static void getcongif() {
        Log.d(TAG, "getcongif: ");
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // 获取SD卡对应的存储目录
            File sdCardDir = Environment.getExternalStorageDirectory();
            System.out.println("----------------" + sdCardDir);
            // 获取指定文件对应的输入流
            try {
                FileInputStream fis = new FileInputStream(sdCardDir.getCanonicalPath() + configFILE_NAME);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                parseXMLWithPull(br);
            } catch (IOException e) {
                Log.d(TAG, "getcongif: IOException e");
                e.printStackTrace();
            }
        }
    }

    //解析xml文件
    public static void parseXMLWithPull(BufferedReader bufferedReader) {
        Log.d(TAG, "parseXMLWithPull: ");
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(bufferedReader /*new StringReader(xmlData)*/);
            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    // 开始解析某个结点
                    case XmlPullParser.START_TAG: {
                        if ("standardtest_time".equals(nodeName)) {
                            standardtest_time = Integer.parseInt(xmlPullParser.nextText());
                        }
                        break;
                    }
                    // 完成解析某个结点
                    case XmlPullParser.END_TAG: {
                        if ("resources".equals(nodeName)) {
                            Log.d("parseXMLWithPull", "standardtest_time" + standardtest_time);
                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (Exception e) {
            Log.d(TAG, "parseXMLWithPull: Exception e");
            e.printStackTrace();
        }
    }


}


