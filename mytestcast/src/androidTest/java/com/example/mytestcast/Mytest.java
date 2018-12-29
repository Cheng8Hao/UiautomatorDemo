package com.example.mytestcast;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Rect;
import android.os.Environment;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.UiWatcher;
import android.support.test.uiautomator.Until;
import android.util.Log;
import android.view.KeyEvent;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import java.util.List;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.view.KeyEvent.KEYCODE_AT;
import static android.view.KeyEvent.KEYCODE_PERIOD;
import static java.lang.Thread.sleep;

/**
 * Created by chenghao on 18-11-23.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class Mytest {
    private static final String TAG = "Mytest";
    //获取测试包的Context
    Context testContext = InstrumentationRegistry.getContext();
    //获取被测应用的Context
    //Context testedContext = InstrumentationRegistry.getTargetContext().getApplicationContext();
    private static UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
    int displayHeight = mDevice.getDisplayHeight();
    int displayWidth = mDevice.getDisplayWidth();
    private static final String endCall = "input  keyevent  KEYCODE_ENDCALL";
    private static final String dialCMD = "am start -a android.intent.action.CALL tel:";
    private static final String ChormePackageName = "com.android.chrome";
    private static final String MessagePackageName = "com.google.android.apps.messaging";
    private static final String DialerPackageName = "com.android.dialer";
    private static final String CameraPackageName = "com.mediatek.camera";
    private static final String GmailPackageName = "com.google.android.gm.lite";
    private static final String googledMusicPackageName = "com.google.android.music";
    private static final String androidMusicPackageName = "com.android.music";
    private static final String PlayStorePackageName = "com.google.android.gms";
    public static String FilesPackageName;
    public static String MusicPackageName;
    private static final String GamesPackageName = "com.outfit7.talkingtom2free";
    private static final String settingsPackageName = "com.android.settings";
    private static final String QQPackageName = "com.tencent.qqlite";
    private static final String FILE_NAME = "/uiautomatorTest.txt";
    private static final int timeOut = 5000;//5秒
    private static final int fifteenminstime = 1 * 60 * 1000;//15分钟
    private static final int tenseconds = 10 * 1000;//10秒
    private static final int tenmins = 30 * 1000;//10 * 60 * 1000;//10分钟
    private static final int onemins = 60 * 1000;//1分钟
    private static int testcaseNo = 0; //测试case的序列号
    private static final String configFILE_NAME = "/myconfig.xml";
/*    private static String qq_num = "18067112583";
    private static String qq_password = "chenghaoasd789";*/


    //get from config.xml 先注释了方便debug
    private static String CooperatingPhoneNum;
    private static String UTDPhoneNum;
    private static int openWebTest_time;
    private static int call_count;
    private static int call_times;
    private static int endCall_count;
    private static int endCall_times;
    private static String targetGmail;
    private static String myGmailNumber;
    private static String mygmailpassword;
    private static int takePicture_count;
    private static int takeVideo_time;
    private static int playMusic_time;
    private static int playVideo_time;
    private static int sendmail_count;
    private static int playgames_time;
    private static int qqtalk_time;
    private static int bootThenStandby_time;

/*
    private static String CooperatingPhoneNum = "18067112583";
    private static String UTDPhoneNum = "18557535936";//本机号码
    private static int openWebTest_time = 80 * 1000;//30 * 60 * 1000;//浏览网页的时间
    private static int call_count = 3;//2;//通话次数
    private static int call_times = 40 * 1000;//3 * 60 * 1000;//单次通话时间
    private static int endCall_count = 3;//3;//通话拒接次数
    private static int endCall_times = 30 * 1000;//20 * 1000;//通话拒接时间间隔
    private static String targetGmail = "610975645ch@gmail.com";
    private static String myGmailNumber = "18067112583ch@gmail.com";
    private static String mygmailpassword = "chenghaoasd789";
    private static int takePicture_count = 10;//拍照数量
    private static int takeVideo_time = 40 * 1000;//5 * 60 * 1000;//录像时间
    private static int playMusic_time = 40 * 1000;//5 * 60 * 1000;//播放音乐时间
    private static int playVideo_time = 3 * 60 * 1000;// 60 * 60 * 1000;//播放总视频时间
    private static int sendmail_count = 4;//4;//发送邮件的个数
    private static int playgames_time = 80 * 1000;//60 * 60 * 1000;//游戏时间
    private static int qqtalk_time = 2;//30mins//qq聊天的时间
    private static int bootThenStandby_time =20*1000;*/


    @BeforeClass
    public static void beforeClass() throws UiObjectNotFoundException, IOException, InterruptedException {
        //测试项目开始前运行（仅一次），如清除缓存数据、安装应用等
        Log.d(TAG, "beforeClass: ");
        //获取权限
        getInstrumentation().getUiAutomation().executeShellCommand("pm grant " + InstrumentationRegistry.getTargetContext().getPackageName() + " android.permission.WRITE_EXTERNAL_STORAGE");
        getInstrumentation().getUiAutomation().executeShellCommand("pm grant " + InstrumentationRegistry.getTargetContext().getPackageName() + " android.permission.READ_EXTERNAL_STORAGE");
        //获取包名
        getlistpackages();
        //每次开始测试前先删除测试报告
        deletefile();
        //设置手机熄灭屏幕时间，除去熄灭屏幕解锁方式
        setDisplayTimeandScreenLock();
        // 序号	测试项描述	测试开始时间	电池电压（mV）	电池电量(%)	测试结束时间	电池电压（mV）	电池电量(%) 单项测试时长（mins）	备注
        write("Serial number" + "\t" + "Description" + "\t" + "StartTime" + "\t" + "Battery voltage(mV)" + "\t" + "Battery electricity(%)" + "\t" + "EndTime" + "\t" + "Battery voltage(mV)" + "\t" + "Battery electricity(%)" + "\t" + "Item test time(min)" + "\t" + "note" + "\t" + "\n");
        //测试开始前获取config文件信息
        getcongif();
    }

    @AfterClass
    public static void afterClass() {
        //测试项目结束时运行（仅一次），如卸载应用等
        Log.d(TAG, "afterClass: ");
    }

    @Before
    public void before() {
        //测试开始前运行（每个@Test运行前都会运行一次），如打开应用等
        Log.d(TAG, "before: ");
        testcaseNo++;
        try {
            if (!mDevice.isScreenOn()) {
                Log.e(TAG, "isScreenOn==" + mDevice.isScreenOn());
                mDevice.wakeUp();
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Device wakeUp exception!");
            e.printStackTrace();
        }
        MyWatcher myWatcher = new MyWatcher(mDevice);
        Log.d(TAG, "registwatcher: ");
        mDevice.registerWatcher("testwatcher", myWatcher);
        Log.d(TAG, "before: testcaseNo=" + testcaseNo);
    }

    @After
    public void after() {
        //测试结束后（每个@Test结束后都会运行一次），如退出应用等
        Log.d(TAG, "after: ");
    }

    //开机等待10mins
    @Test
    public void test_001() throws InterruptedException, UiObjectNotFoundException, IOException {
        int startTime = (int) System.currentTimeMillis();
        write(testcaseNo + "\t" + "Boot Then Standby" + "\t" + getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t");
        sleep(tenmins);
        int endTime = (int) System.currentTimeMillis();
        int testTime = ((endTime - startTime) / 1000) / 60;
        Log.d(TAG, "test_001: testTime=" + testTime);
        write(getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t" + testTime + "\t" + "test finish" + "\t" + "\n");
    }

    //call
    @Test
    public void test_002() throws InterruptedException, UiObjectNotFoundException, IOException, RemoteException {
        write(testcaseNo + "\t" + "Call test" + "\t" + getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t");
        int startTime = (int) System.currentTimeMillis();
        int i;
        for (i = 0; i < call_count; i++) {
            Log.d(TAG, "test_002: i===" + i);
            mDevice.executeShellCommand(dialCMD + CooperatingPhoneNum);
            Log.d(TAG, "test_002: dialstart");
            sleep(call_times);
            mDevice.executeShellCommand(endCall);
            Log.d(TAG, "test_002: endCall");
        }
        int endTime = (int) System.currentTimeMillis();
        int testTime = ((endTime - startTime) / 1000) / 60;
        write(getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t" + testTime + "\t" + "test finish" + "\t" + "\n");
    }

    //endcall
    @Test
    public void test_003() throws InterruptedException, UiObjectNotFoundException, IOException {
        write(testcaseNo + "\t" + "EndCall test" + "\t" + getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t");
        int startTime = (int) System.currentTimeMillis();
        int i;
        for (i = 0; i < endCall_count; i++) {
            Log.d(TAG, "test_003: i===" + i);
            mDevice.executeShellCommand(dialCMD + UTDPhoneNum);
            Log.d(TAG, "test_003: dialCall");
            Log.d(TAG, "test_003: sleep before");
            sleep(endCall_times);
            Log.d(TAG, "test_003: sleep after");
            mDevice.executeShellCommand(endCall);
            Log.d(TAG, "test_003: endCall");
        }
        int endTime = (int) System.currentTimeMillis();
        int testTime = ((endTime - startTime) / 1000) / 60;
        write(getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t" + testTime + "\t" + "test finish" + "\t" + "\n");

    }

    //camera
    @Test
    public void test_004() throws IOException, InterruptedException, UiObjectNotFoundException {
        write(testcaseNo + "\t" + "Camera test" + "\t" + getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t");
        int startTime = (int) System.currentTimeMillis();
        startApp(CameraPackageName, timeOut);
        //后置摄像头拍10张
        UiObject backshutterButton = new UiObject(new UiSelector().resourceId("com.mediatek.camera:id/shutter_image"));
        if (backshutterButton.exists()) {
            int i;
            for (i = 0; i < takePicture_count; i++) {
                backshutterButton.clickAndWaitForNewWindow();
            }
        } else {
            UiObject backshutterButton2 = new UiObject(new UiSelector().resourceId("com.mediatek.camera:id/shutter_button"));
            if (backshutterButton2.exists()) {
                int i;
                for (i = 0; i < takePicture_count; i++) {
                    backshutterButton2.clickAndWaitForNewWindow();
                }
            }
        }
        //如果有前置摄像头的话,切换摄像头，前置摄像头拍10张，
        UiObject switcherButton = new UiObject(new UiSelector().resourceId("com.mediatek.camera:id/camera_switcher"));
        if (switcherButton.exists()) {
            switcherButton.clickAndWaitForNewWindow();
        }
        sleep(1000);
        UiObject switcherButton2 = new UiObject(new UiSelector().resourceId("com.mediatek.camera:id/onscreen_camera_picker"));
        if (switcherButton2.exists()) {
            switcherButton2.clickAndWaitForNewWindow();
        }
        sleep(1000);
        //前置摄像头拍10张
        UiObject frontshutterButton = new UiObject(new UiSelector().resourceId("com.mediatek.camera:id/shutter_image"));
        if (frontshutterButton.exists()) {
            int i;
            for (i = 0; i < takePicture_count; i++) {
                frontshutterButton.clickAndWaitForNewWindow();
            }
        } else {
            UiObject frontshutterButton2 = new UiObject(new UiSelector().resourceId("com.mediatek.camera:id/shutter_button"));
            if (frontshutterButton2.exists()) {
                int i;
                for (i = 0; i < takePicture_count; i++) {
                    frontshutterButton2.clickAndWaitForNewWindow();
                }
            }
        }
        int endTime = (int) System.currentTimeMillis();
        int testTime = ((endTime - startTime) / 1000) / 60;
        closeAPP(CameraPackageName);
        write(getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t" + testTime + "\t" + "test finish" + "\t" + "\n");
    }

    //takeVideo
    @Test
    public void test_005() throws IOException, InterruptedException, UiObjectNotFoundException {
        write(testcaseNo + "\t" + "TakeVideo test" + "\t" + getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t");
        int startTime = (int) System.currentTimeMillis();
        startApp(CameraPackageName, timeOut);
        //A15，F1
        UiObject videoshutterButton1 = new UiObject(new UiSelector().resourceId("com.mediatek.camera:id/shutter_button_video"));
        if (videoshutterButton1.exists()) {
            videoshutterButton1.clickAndWaitForNewWindow();
            sleep(takeVideo_time);
            videoshutterButton1.clickAndWaitForNewWindow();
        }

        //A63,5531
        UiObject VideoButton = new UiObject(new UiSelector().textContains("video"));
        if (VideoButton.exists()) {
            VideoButton.clickAndWaitForNewWindow();
            UiObject videoshutterButton = new UiObject(new UiSelector().resourceId("com.mediatek.camera:id/shutter_image"));
            if (videoshutterButton.exists()) {
                videoshutterButton.clickAndWaitForNewWindow();
                sleep(takeVideo_time);
                //停止录像
                UiObject stopVideoButton = new UiObject(new UiSelector().resourceId("com.mediatek.camera:id/video_stop_shutter"));
                if (stopVideoButton.exists()) {
                    stopVideoButton.clickAndWaitForNewWindow();
                }
            }
        }
        int endTime = (int) System.currentTimeMillis();
        int testTime = ((endTime - startTime) / 1000) / 60;//mins
        closeAPP(CameraPackageName);
        write(getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t" + testTime + "\t" + "test finish" + "\t" + "\n");
    }

    //qq 聊天 +playmusic
    @Test
    public void test_006() throws InterruptedException, UiObjectNotFoundException, IOException {
        int startTime = (int) System.currentTimeMillis();
        write(testcaseNo + "\t" + "QQ + music" + "\t" + getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t");
        //play music begin
        startApp(MusicPackageName, timeOut);
        sleep(3000);
        if (MusicPackageName.equals(googledMusicPackageName)) {
            Log.d(TAG, "test_006: open googledMusic ");
            UiObject skip = new UiObject(new UiSelector().resourceId("com.google.android.music:id/skip_button"));
            skip.clickAndWaitForNewWindow();
            //左滑
            mDevice.swipe(displayWidth - 10, displayHeight / 2, displayWidth / 3, displayHeight / 2, displayWidth / 9);
            UiObject Shuffle_all = new UiObject(new UiSelector().text("Shuffle all"));
            Shuffle_all.clickAndWaitForNewWindow();
            UiObject song = new UiObject(new UiSelector().text("Nana-Lonely"));
            song.clickAndWaitForNewWindow();
            UiObject repeat = new UiObject(new UiSelector().resourceId("com.google.android.music:id/repeat"));
            repeat.clickAndWaitForNewWindow();
        } else if (MusicPackageName.equals(androidMusicPackageName)) {
            Log.d(TAG, "test_006: open androidMusic ");
            UiObject Playlists = new UiObject(new UiSelector().text("Playlists"));
            Playlists.clickAndWaitForNewWindow();
            UiObject Recently = new UiObject(new UiSelector().text("Recently added"));
            Recently.clickAndWaitForNewWindow();
            UiObject options = new UiObject(new UiSelector().description("More options").className("android.widget.ImageButton"));
            options.clickAndWaitForNewWindow();
            UiObject shuffle = new UiObject(new UiSelector().text("Party shuffle"));
            shuffle.clickAndWaitForNewWindow();
        }
        mDevice.pressHome();
        Log.d(TAG, "test_006: play music end");
        //play music end
        //start qq
        startApp(QQPackageName, timeOut);
        //qq因为有验证码，需要在测试前人工登陆，不然测试会中断
        UiObject contact = new UiObject(new UiSelector().text("联系人"));
        contact.clickAndWaitForNewWindow();
        UiObject myfriend = new UiObject(new UiSelector().text("我的好友"));
        myfriend.clickAndWaitForNewWindow();
        UiObject babyQ = new UiObject(new UiSelector().text("babyQ"));
        babyQ.clickAndWaitForNewWindow();
        int endTime;
        while (true) {
            Log.d(TAG, "test_006: while true");
            UiObject input = new UiObject(new UiSelector().resourceId("com.tencent.qqlite:id/input"));
            input.setText("hello?");
            sleep(5000);
            UiObject send = new UiObject(new UiSelector().resourceId("com.tencent.qqlite:id/fun_btn"));
            send.clickAndWaitForNewWindow();
            sleep(5000);
            endTime = (int) System.currentTimeMillis();
            Log.d(TAG, "test_006: startTime="+startTime);
            Log.d(TAG, "test_006: endTime="+endTime);
            int timegap=Math.abs(endTime - startTime);
            Log.d(TAG, "test_006: timegap="+timegap);
            if (timegap>= qqtalk_time) {
                Log.d(TAG, "test_006: break");
                break;
            }
        }
        closeAPP(QQPackageName);
        Log.d(TAG, "test_006: startTime="+startTime);
        Log.d(TAG, "test_006: endTime="+endTime);
        int testTime = ((endTime - startTime) / 1000) / 60;
        Log.d(TAG, "test_006: testTime="+testTime);
        write(getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t" + testTime + "\t" + "test finish" + "\t" + "\n");
    }

    //openweb
    @Test
    public void test_007() throws UiObjectNotFoundException, InterruptedException, IOException {
        write(testcaseNo + "\t" + "Browser test" + "\t" + getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t");
        int startTime = (int) System.currentTimeMillis();
        startApp(ChormePackageName, timeOut); //启动app
        //得到浏览器中的网页输入框
        boolean ispass = true;
        int testTime = -1;
        int endTime = -1;
        while (ispass) {
            UiObject edit = new UiObject(new UiSelector().className("android.widget.EditText"));
            edit.clickAndWaitForNewWindow();
            //edit.clearTextField();
            mDevice.pressDelete();
            edit.setText("https://www.baidu.com");
            mDevice.pressEnter();//回车进行浏览，在部分手机不支持回车浏览，可以使用上面的方式得到浏览按钮在点击进行浏览
            sleep(tenseconds);
            endTime = (int) System.currentTimeMillis();
            if (endTime - startTime > openWebTest_time) {
                Log.d(TAG, "case1:startTime " + startTime);
                Log.d(TAG, "case1: endTime=" + endTime);
                ispass = false;
            }
        }
        testTime = ((endTime - startTime) / 1000) / 60;   //分钟
        closeAPP(ChormePackageName);
        write(getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t" + testTime + "\t" + "test finish" + "\t" + "\n");
    }

    //sendmail
    @Test
    public void test_008() throws InterruptedException, UiObjectNotFoundException, IOException {
        write(testcaseNo + "\t" + "SendMail test" + "\t" + getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t");
        int startTime = (int) System.currentTimeMillis();
        startApp(GmailPackageName, timeOut);
        sleep(5000);
        UiObject GOT_IT = new UiObject(new UiSelector().text("GOT IT"));
        GOT_IT.clickAndWaitForNewWindow();
        UiObject add = new UiObject(new UiSelector().text("Add an email address"));
        add.clickAndWaitForNewWindow();
        UiObject Google = new UiObject(new UiSelector().text("Google"));
        Google.clickAndWaitForNewWindow();
        sleep(30000);
        if (mDevice.hasObject(By.text("Email or phone"))) {
            Log.d(TAG, "gmail: Email or phone");
            UiObject Email = new UiObject(new UiSelector().text("Email or phone"));
            Email.setText(myGmailNumber);
        }
        UiObject Next = new UiObject(new UiSelector().text("Next"));
        Next.clickAndWaitForNewWindow();
        sleep(15000);
        UiObject password = new UiObject(new UiSelector().text("Enter your password"));
        password.setText(mygmailpassword);
        UiObject Next2 = new UiObject(new UiSelector().text("Next"));
        Next2.clickAndWaitForNewWindow();
        UiObject agree = new UiObject(new UiSelector().text("I agree"));
        agree.clickAndWaitForNewWindow();
        sleep(30000);
        UiObject MORE = new UiObject(new UiSelector().text("MORE"));
        MORE.clickAndWaitForNewWindow();
        sleep(15000);
        UiObject agree2 = new UiObject(new UiSelector().text("I AGREE"));
        agree2.clickAndWaitForNewWindow();
        sleep(30000);
        UiObject taketoGmail = new UiObject(new UiSelector().text("TAKE ME TO GMAIL"));
        taketoGmail.clickAndWaitForNewWindow();
        for (int j = 0; j < sendmail_count; j++) {
            Log.d(TAG, "test_007: j=" + j);
            UiObject compose_button = new UiObject(new UiSelector().resourceId("com.google.android.gm.lite:id/compose_button"));
            compose_button.clickAndWaitForNewWindow();
            sleep(2000);
            String input = targetGmail;
            sleep(2000);
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                Log.d(TAG, "c=: " + (int) c);
                if (c >= 48 && c <= 57) {
                    mDevice.pressKeyCode(c - 41);
                } else if (c >= 97 && c <= 122) {
                    mDevice.pressKeyCode(c - 68);
                } else if (c >= 65 && c <= 90) {
                    mDevice.pressKeyCode(59);
                    sleep(20);
                    mDevice.pressKeyCode(c - 36);
                } else if (c == 32) {
                    mDevice.pressKeyCode(62);
                } else if (c == 64) {
                    mDevice.pressKeyCode(KEYCODE_AT);
                } else if (c == 46) {
                    mDevice.pressKeyCode(KEYCODE_PERIOD);
                }
            }
            UiObject send = new UiObject(new UiSelector().resourceId("com.google.android.gm.lite:id/send"));
            send.clickAndWaitForNewWindow();
        }
        closeAPP(GmailPackageName);
        closeAPP(MusicPackageName);
        int endTime = (int) System.currentTimeMillis();
        int testTime = ((endTime - startTime) / 1000) / 60;
        write(getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t" + testTime + "\t" + "test finish" + "\t" + "\n");
    }

    //_playvideo
    //adb push 720.mp4 sdcard/   是push到手机内部sd卡上，不是外置sd卡上;
    // 而且需要重启手机才能在filesGO中找到文件
    @Test
    public void test_009() throws IOException, UiObjectNotFoundException, InterruptedException {
        write(testcaseNo + "\t" + "PlayVideo test" + "\t" + getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t");
        int startTime = (int) System.currentTimeMillis();
        startApp(FilesPackageName, timeOut);
        if (FilesPackageName.contains("files")) {
            if (mDevice.hasObject(By.text("CONTINUE"))) {
                UiObject continueButton = new UiObject(new UiSelector().textContains("continue"));
                continueButton.clickAndWaitForNewWindow();
            }
            {
                UiObject continueButton = new UiObject(new UiSelector().text("Clean"));
                continueButton.clickAndWaitForNewWindow();
            }
            if (mDevice.hasObject(By.text("Browse"))) {
                UiObject BrowseButton = new UiObject(new UiSelector().text("Browse"));
                BrowseButton.clickAndWaitForNewWindow();
            }
            //F1 old version
            if (mDevice.hasObject(By.text("Files"))) {
                UiObject FilesButton = new UiObject(new UiSelector().text("Files"));
                FilesButton.clickAndWaitForNewWindow();
            }
            sleep(2000);
            mDevice.swipe(displayWidth / 2, displayHeight / 4 * 3, displayWidth / 2, displayHeight / 4, displayHeight / 4);
            mDevice.swipe(displayWidth / 2, displayHeight / 4 * 3, displayWidth / 2, displayHeight / 4, displayHeight / 4);
            mDevice.swipe(displayWidth / 2, displayHeight / 4 * 3, displayWidth / 2, displayHeight / 4, displayHeight / 4);
            mDevice.swipe(displayWidth / 2, displayHeight / 4 * 3, displayWidth / 2, displayHeight / 4, displayHeight / 4);
            int flag = 0;
            UiObject VideoButton = new UiObject(new UiSelector().textContains("Videos"));
            VideoButton.clickAndWaitForNewWindow();
            //如果filesgo版本比较新，有田字型switch按钮
            if (mDevice.hasObject(By.res("com.google.android.apps.nbu.files:id/view_mode_switch"))) {
                UiObject BrowseButton = new UiObject(new UiSelector().resourceId("com.google.android.apps.nbu.files:id/view_mode_switch"));
                BrowseButton.clickAndWaitForNewWindow();
                mDevice.swipe(displayWidth / 2, displayHeight / 2, displayWidth / 2, displayHeight / 7, displayHeight / 20);
                sleep(2000);
            } else {
                UiObject optionButton = new UiObject(new UiSelector().description("More options"));
                optionButton.clickAndWaitForNewWindow();
                UiObject switchButton = new UiObject(new UiSelector().textContains("Switch to list"));
                if (switchButton.exists()) {
                    switchButton.clickAndWaitForNewWindow();
                } else {
                    mDevice.pressBack();
                }
            }
            UiObject ALL = new UiObject(new UiSelector().textContains("ALL"));
            ALL.clickAndWaitForNewWindow();
            int i;
            //15分钟的视频，循环播放4次
            for (i = 0; i < (playVideo_time / fifteenminstime); i++) {
                flag++;
                Log.d(TAG, "flag: " + flag);
                UiObject Button720p = new UiObject(new UiSelector().textContains("720P.mp4"));
                Button720p.clickAndWaitForNewWindow();
                if (mDevice.hasObject(By.text("Photos"))) {
                    UiObject openwith = new UiObject(new UiSelector().text("Photos"));
                    openwith.clickAndWaitForNewWindow();
                } else if (mDevice.hasObject(By.textContains("Video"))) {
                    UiObject openwith = new UiObject(new UiSelector().textContains("Video"));
                    openwith.clickAndWaitForNewWindow();
                }
                if (mDevice.hasObject(By.text("ALWAYS"))) {
                    UiObject openwithphotos = new UiObject(new UiSelector().text("ALWAYS"));
                    openwithphotos.clickAndWaitForNewWindow();
                }
                sleep(fifteenminstime);
                mDevice.pressBack();
            }
            closeAPP(FilesPackageName);
        }
        //如果预置的是filemanager
        else if (FilesPackageName.contains("filemanager")) {
            UiObject InternalItem = new UiObject(new UiSelector().textContains("Internal"));
            InternalItem.clickAndWaitForNewWindow();
            mDevice.swipe(displayWidth / 2, displayHeight / 4 * 3, displayWidth / 2, displayHeight / 4, displayHeight / 4);
            mDevice.swipe(displayWidth / 2, displayHeight / 4 * 3, displayWidth / 2, displayHeight / 4, displayHeight / 4);
            mDevice.swipe(displayWidth / 2, displayHeight / 4 * 3, displayWidth / 2, displayHeight / 4, displayHeight / 4);
            mDevice.swipe(displayWidth / 2, displayHeight / 4 * 3, displayWidth / 2, displayHeight / 4, displayHeight / 4);
            int i;
            int flag = 0;
            //15分钟的视频，循环播放4次
            for (i = 0; i < 4; i++) {
                flag++;
                Log.d(TAG, "flag: " + flag);
                UiObject Button720p = new UiObject(new UiSelector().textContains("720P.mp4"));
                Button720p.clickAndWaitForNewWindow();
                if (mDevice.hasObject(By.text("Photos"))) {
                    UiObject openwith = new UiObject(new UiSelector().text("Photos"));
                    openwith.clickAndWaitForNewWindow();
                } else if (mDevice.hasObject(By.textContains("Video"))) {
                    UiObject openwith = new UiObject(new UiSelector().textContains("Video"));
                    openwith.clickAndWaitForNewWindow();
                }
                if (mDevice.hasObject(By.text("ALWAYS"))) {
                    UiObject openwithphotos = new UiObject(new UiSelector().text("ALWAYS"));
                    openwithphotos.clickAndWaitForNewWindow();
                }
                sleep(fifteenminstime);
                mDevice.pressBack();
            }
            closeAPP(FilesPackageName);
        }
        int endTime = (int) System.currentTimeMillis();
        int testTime = ((endTime - startTime) / 1000) / 60;
        write(getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t" + testTime + "\t" + "test finish" + "\t" + "\n");

    }

    //playgames
    //adb install talkingcat.apk
    //前提要插入sim卡
    @Test
    public void test_010() throws IOException, InterruptedException, UiObjectNotFoundException {
        int startTime = (int) System.currentTimeMillis();
        write(testcaseNo + "\t" + "PlayGames test" + "\t" + getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t");
        startApp(GamesPackageName, timeOut);
        sleep(15000);
        if (mDevice.hasObject(By.textContains("登录方式"))) {
            Log.d(TAG, "test_games: 登录方式");
            mDevice.pressBack();
        }
        sleep(5000);
        if (mDevice.hasObject(By.text("确定"))) {
            Log.d(TAG, "test_games: 确定");
            UiObject continueButton = new UiObject(new UiSelector().text("确定"));
            continueButton.clickAndWaitForNewWindow();
        }
        if (mDevice.hasObject(By.text("OK"))) {
            Log.d(TAG, "test_games: ok");
            UiObject OKButton2 = new UiObject(new UiSelector().text("OK"));
            //UiObject OKButton2 = new UiObject(new UiSelector().resourceId("com.outfit7.talkingtom2free:id/sharingAgeScreeningButtonOk"));
            //UiObject OKButton2 = new UiObject(new UiSelector().textContains("确定"));
            Rect OKButton2Bounds = null;
            OKButton2Bounds = OKButton2.getBounds();
            int height = OKButton2Bounds.height();
            int centerX = OKButton2Bounds.centerX();
            int centerY = OKButton2Bounds.centerY();
            int top = OKButton2Bounds.top;
            int left = OKButton2Bounds.left;
            int bottom = OKButton2Bounds.bottom;
            int right = OKButton2Bounds.right;
            Log.d(TAG, "test_games: " + height + "  " + centerX + " " + centerY + " " + top + " " + left + " " + right + " " + bottom);
            mDevice.swipe(centerX, top - height, centerX, top - 2 * height, 10);
            OKButton2.clickAndWaitForNewWindow();
            sleep(2000);
        }
        if (mDevice.hasObject(By.text("NO"))) {
            Log.d(TAG, "test_games: NO");
            UiObject OKButton3 = new UiObject(new UiSelector().textContains("NO"));
            OKButton3.clickAndWaitForNewWindow();
        }
        //进入游戏，时不时点击一下屏幕就ok啦
        int i;
        int j = playgames_time / tenseconds;
        for (i = 0; i < j; i++) {
            Log.d(TAG, "test_games: into for()");
            mDevice.click(displayWidth / 2, displayHeight / 2);
            mDevice.click(displayWidth / 2, displayHeight / 2);
            sleep(tenseconds);
        }
        closeAPP(GamesPackageName);
        int endTime = (int) System.currentTimeMillis();
        int testTime = ((endTime - startTime) / 1000) / 60;
        write(getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t" + testTime + "\t" + "test finish" + "\t" + "\n");

    }

//util method begin *****************************************************************************************************************************

    //获取电池信息  格式：level:100，voltage:4232
    public String getBatterryInfo() throws IOException {
        Log.d(TAG, "getBatterryInfo: ");
        String batteryinfo = null;
        batteryinfo = mDevice.executeShellCommand("dumpsys battery");
        Log.d(TAG, "getBatterryInfo: " + batteryinfo);
        int level = batteryinfo.indexOf("level");
        int scale = batteryinfo.indexOf("scale");
        int voltage = batteryinfo.lastIndexOf("voltage");
        int temperature = batteryinfo.indexOf("temperature");
        String btlevel = batteryinfo.substring(level, scale).trim();
        String btvoltage = batteryinfo.substring(voltage, temperature).trim();
        Log.d(TAG, "btlevel=" + btlevel + " " + "btvoltage=" + btvoltage);//btlevel=level:100 btvoltage=voltage:4360
        //batteryinfo = btlevel + "\n" + btvoltage ;
        batteryinfo = btlevel + "," + btvoltage;
        return batteryinfo;
    }

    //获取电压
    public String getBatterryVoltage() throws IOException {
        Log.d(TAG, "getBatterryInfo: ");
        String batteryinfo = null;
        batteryinfo = mDevice.executeShellCommand("dumpsys battery");
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

    //获取电量
    public String getBatterryElectric() throws IOException {
        Log.d(TAG, "getBatterryInfo: ");
        String batteryinfo = null;
        batteryinfo = mDevice.executeShellCommand("dumpsys battery");
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

    // 获取日期信息 格式：yyyyMMddHHmmss
    public String getTimeInfo() {//y-M-d h:m:s   yyyy-MM-dd-HHmm:ss
        SimpleDateFormat format = new SimpleDateFormat("y-M-d  H:m:s");
        String time = format.format(Calendar.getInstance().getTime());
        Log.d(TAG, "完整的时间和日期： " + time);
        String testtime = time;
        return testtime;
    }

    //打开apk
    public static void startApp(String packageName, int timeOut) {
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
        context.startActivity(intent);
        mDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), timeOut);  // Wait for the app to appear
    }

    //关闭app
    public static void closeAPP(String sPackageName) throws IOException {
        Log.d(TAG, "closeAPP: ");
        mDevice.executeShellCommand("am force-stop " + sPackageName);//通过命令行关闭app
        //mDevice.executeShellCommand("pm clear " + sPackageName);
    }

    //调节音量
    public static void adjustVolume(boolean up, int times) throws UiObjectNotFoundException, InterruptedException {
        if (up) {
            for (int i = 0; i < times; i++) {
                mDevice.pressKeyCode(KeyEvent.KEYCODE_VOLUME_UP);
                sleep(1000);
            }
        } else {
            for (int j = 0; j < times; j++) {
                mDevice.pressKeyCode(KeyEvent.KEYCODE_VOLUME_DOWN);
                sleep(1000);
            }
        }
    }


    //读取sd卡下的log文件
    private String read() throws IOException {
        // 如果手机插入了SD卡，而且应用程序具有访问SD的权限
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // 获取SD卡对应的存储目录
            File sdCardDir = Environment.getExternalStorageDirectory();
            System.out.println("----------------" + sdCardDir);
            // 获取指定文件对应的输入流
            FileInputStream fis = new FileInputStream(sdCardDir.getCanonicalPath() + FILE_NAME);
            // 将指定输入流包装成BufferedReader
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            StringBuilder sb = new StringBuilder("");
            String line = null;
            // 循环读取文件内容
            while ((line = br.readLine()) != null) {
                Log.d(TAG, "line=" + line);
                sb.append(line);
            }
            // 关闭资源
            br.close();
            return sb.toString();
        }
        return null;
    }


    //log文件存储到sd卡下
    private static void write(String content) throws IOException {
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

    //每次测试前删除之前测试生成的测试报告
    private static void deletefile() throws IOException {
        // 如果手机插入了SD卡，而且应用程序具有访问SD的权限
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.d(TAG, "具有访问SD的权限: ");
            // 获取SD卡的目录
            File sdCardDir = Environment.getExternalStorageDirectory();
            Log.d(TAG, "sdCardDir.getCanonicalPath(): " + sdCardDir.getCanonicalPath());
            File targetFile = new File(sdCardDir.getCanonicalPath() + FILE_NAME);
            if (targetFile.exists()) {
                Log.d(TAG, "deletefile: targetFile.exists()");
                targetFile.delete();
            }

        }
    }

    public class MyWatcher implements UiWatcher {
        private UiDevice mDevice;

        public MyWatcher(UiDevice device) {
            mDevice = device;
        }

        @Override
        public boolean checkForCondition() {
            if (mDevice.hasObject(By.text("立即开启"))) {
                Log.d(TAG, "qqmail: 立即开启");
                mDevice.pressBack();
                return true;
            }

            if (mDevice.hasObject(By.text("ACCEPT"))) {
                Log.d(TAG, "ACCEPT");
                UiObject ACCEPT = new UiObject(new UiSelector().text("ACCEPT"));
                try {
                    ACCEPT.clickAndWaitForNewWindow();
                } catch (UiObjectNotFoundException e) {
                    e.printStackTrace();
                }
                return true;
            }

            if (mDevice.hasObject(By.text("ALLOW"))) {
                Log.d(TAG, "filemanager: ALLOW");
                UiObject ALLOW = new UiObject(new UiSelector().text("ALLOW"));
                try {
                    ALLOW.clickAndWaitForNewWindow();
                    sleep(2000);
                } catch (UiObjectNotFoundException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            }
            //A15 chorme
            if (mDevice.hasObject(By.text("ACCEPT & CONTINUE"))) {
                Log.d(TAG, "chrome: ACCEPT & CONTINUE");
                UiObject ALLOW = new UiObject(new UiSelector().text("ACCEPT & CONTINUE"));
                try {
                    ALLOW.clickAndWaitForNewWindow();
                } catch (UiObjectNotFoundException e) {
                    e.printStackTrace();
                }
                if (mDevice.hasObject(By.text("NEXT"))) {
                    Log.d(TAG, "chrome: NEXT");
                    UiObject NEXT = new UiObject(new UiSelector().text("NEXT"));
                    try {
                        NEXT.clickAndWaitForNewWindow();
                    } catch (UiObjectNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (mDevice.hasObject(By.text("Google"))) {
                        Log.d(TAG, "chrome1: Google");
                        UiObject Google = new UiObject(new UiSelector().text("Google"));
                        try {
                            Google.clickAndWaitForNewWindow();
                        } catch (UiObjectNotFoundException e) {
                            e.printStackTrace();
                        }
                        if (mDevice.hasObject(By.text("OK"))) {
                            Log.d(TAG, "chrome1: OK");
                            UiObject OK = new UiObject(new UiSelector().text("OK"));
                            try {
                                OK.clickAndWaitForNewWindow();
                            } catch (UiObjectNotFoundException e) {
                                e.printStackTrace();
                            }
                            if (mDevice.hasObject(By.text("NO THANKS"))) {
                                Log.d(TAG, "chrome1: NO THANKS");
                                UiObject NOTHANKS = new UiObject(new UiSelector().text("NO THANKS"));
                                try {
                                    NOTHANKS.clickAndWaitForNewWindow();
                                    try {
                                        sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                } catch (UiObjectNotFoundException e) {
                                    e.printStackTrace();
                                }
                                if (mDevice.hasObject(By.text("KEEP GOOGLE"))) {
                                    Log.d(TAG, "chrome2: KEEP GOOGLE");
                                    UiObject KEEPGOOGLE = new UiObject(new UiSelector().text("KEEP GOOGLE"));
                                    try {
                                        KEEPGOOGLE.clickAndWaitForNewWindow();
                                        sleep(2000);
                                    } catch (UiObjectNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    return true;
                                }
                                return true;
                            }
                            return true;
                        }
                        return true;
                    }
                    return true;
                }
                return true;
            }


            if (mDevice.hasObject(By.text("NO THANKS"))) {
                Log.d(TAG, "chrome: NO THANKS");
                UiObject NOTHANKS = new UiObject(new UiSelector().text("NO THANKS"));
                try {
                    NOTHANKS.clickAndWaitForNewWindow();
                } catch (UiObjectNotFoundException e) {
                    e.printStackTrace();
                }
                return true;
            }
            if (mDevice.hasObject(By.text("KEEP GOOGLE"))) {
                Log.d(TAG, "chrome: KEEP GOOGLE");
                UiObject OK = new UiObject(new UiSelector().text("KEEP GOOGLE"));
                try {
                    OK.clickAndWaitForNewWindow();
                } catch (UiObjectNotFoundException e) {
                    e.printStackTrace();
                }
                return true;
            }
            if (mDevice.hasObject(By.text("OK"))) {
                Log.d(TAG, "OK");
                UiObject OK2 = new UiObject(new UiSelector().text("OK"));
                try {
                    OK2.clickAndWaitForNewWindow();
                } catch (UiObjectNotFoundException e) {
                    e.printStackTrace();
                }
                return true;
            }

            //gmail
            if (mDevice.hasObject(By.textContains("Checking info"))) {
                Log.d(TAG, "uiwatcher int to Checking info");
                try {
                    sleep(10000);
                } catch (InterruptedException e) {
                    Log.d(TAG, "InterruptedException: Checking info");
                    e.printStackTrace();
                }
                return true;
            }

            return false;
        }
    }

    //得到文件管理器的包名，确定是filesgo还是filemanager
    public static void getlistpackages() {
        List<PackageInfo> packages = InstrumentationRegistry.getContext().getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            String tmpInfo = null;
            tmpInfo = packageInfo.packageName;
            if (tmpInfo.contains("filemanager")) {
                FilesPackageName = tmpInfo;
            } else if (tmpInfo.contains("files")) {
                FilesPackageName = tmpInfo;
            }
            if (tmpInfo.equals("com.android.music")) {
                MusicPackageName = tmpInfo;
            } else if (tmpInfo.equals("com.google.android.music")) {
                MusicPackageName = tmpInfo;
            }
            Log.d(TAG, "getlistpackages: " + tmpInfo);
        }
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
                        if ("bootThenStandby_time".equals(nodeName)) {
                            bootThenStandby_time = Integer.parseInt(xmlPullParser.nextText()) * onemins;
                        } else if ("CooperatingPhoneNum".equals(nodeName)) {
                            CooperatingPhoneNum = xmlPullParser.nextText();
                        } else if ("UTDPhoneNum".equals(nodeName)) {
                            UTDPhoneNum = xmlPullParser.nextText();
                        } else if ("targetGmail".equals(nodeName)) {
                            targetGmail = xmlPullParser.nextText();
                        } else if ("myGmailNumber".equals(nodeName)) {
                            myGmailNumber = xmlPullParser.nextText();
                        } else if ("mygmailpassword".equals(nodeName)) {
                            mygmailpassword = xmlPullParser.nextText();
                        } else if ("openWebTest_time".equals(nodeName)) {
                            openWebTest_time = Integer.parseInt(xmlPullParser.nextText()) * onemins;
                        } else if ("qqtalk_time".equals(nodeName)) {
                            qqtalk_time = Integer.parseInt(xmlPullParser.nextText()) * onemins;
                        } else if ("call_count".equals(nodeName)) {
                            call_count = Integer.parseInt(xmlPullParser.nextText());
                        } else if ("call_times".equals(nodeName)) {
                            call_times = Integer.parseInt(xmlPullParser.nextText()) * onemins;
                        } else if ("endCall_count".equals(nodeName)) {
                            endCall_count = Integer.parseInt(xmlPullParser.nextText());
                        } else if ("endCall_times".equals(nodeName)) {
                            endCall_times = Integer.parseInt(xmlPullParser.nextText()) * onemins;
                        } else if ("playMusic_time".equals(nodeName)) {
                            playMusic_time = Integer.parseInt(xmlPullParser.nextText()) * onemins;
                        } else if ("takePicture_count".equals(nodeName)) {
                            takePicture_count = Integer.parseInt(xmlPullParser.nextText());
                        } else if ("playVideo_time".equals(nodeName)) {
                            playVideo_time = Integer.parseInt(xmlPullParser.nextText()) * onemins;
                        } else if ("playgames_time".equals(nodeName)) {
                            playgames_time = Integer.parseInt(xmlPullParser.nextText()) * onemins;
                        } else if ("sendmail_count".equals(nodeName)) {
                            sendmail_count = Integer.parseInt(xmlPullParser.nextText());
                        } else if ("takeVideo_time".equals(nodeName)) {
                            takeVideo_time = Integer.parseInt(xmlPullParser.nextText()) * onemins;
                        }
                        break;
                    }
                    // 完成解析某个结点
                    case XmlPullParser.END_TAG: {
                        if ("resources".equals(nodeName)) {
                            Log.d("parseXMLWithPull",
                                    "CooperatingPhoneNum"+CooperatingPhoneNum + "\n" +
                                    "UTDPhoneNum"+UTDPhoneNum + "\n" +
                                    "targetGmail"+targetGmail + "\n" +
                                    "myGmailNumber"+myGmailNumber + "\n" +
                                    "mygmailpassword"+mygmailpassword + "\n" +
                                    "openWebTest_time"+openWebTest_time + "\n" +
                                    "qqtalk_time"+qqtalk_time + "\n" +
                                    "call_count"+call_count + "\n" +
                                    "call_times"+call_times + "\n" +
                                    "endCall_count"+endCall_count + "\n" +
                                    "endCall_times"+endCall_times + "\n" +
                                    "playMusic_time"+playMusic_time + "\n" +
                                    "takePicture_count"+takePicture_count + "\n" +
                                    "playVideo_time"+playVideo_time + "\n" +
                                    "playgames_time"+playgames_time + "\n" +
                                    "sendmail_count"+sendmail_count + "\n" +
                                    "takeVideo_time"+takeVideo_time + "\n"
                            );
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

    //打开settings，设置自动灭屏时间为5mins，设置Screen lock为none
    public static void setDisplayTimeandScreenLock() throws IOException {
        startApp(settingsPackageName, timeOut);
        try {
            UiObject Display = new UiObject(new UiSelector().text("Display"));
            Display.clickAndWaitForNewWindow();
            UiObject Sleep = new UiObject(new UiSelector().text("Sleep"));
            Sleep.clickAndWaitForNewWindow();
            UiObject minutes = new UiObject(new UiSelector().text("5 minutes"));
            minutes.clickAndWaitForNewWindow();
            mDevice.pressBack();

            UiScrollable RecyclerView = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));
            RecyclerView.setMaxSearchSwipes(10);//设置最大可扫动次数
            UiObject Security = new UiObject(new UiSelector().text("Security & location"));//找到“更多”这个选项
            RecyclerView.scrollIntoView(Security);//然后点击它（更多）
            Security.click();

            UiObject Screen = new UiObject(new UiSelector().text("Screen lock"));
            Screen.clickAndWaitForNewWindow();
            UiObject None = new UiObject(new UiSelector().text("None"));
            None.clickAndWaitForNewWindow();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }


        closeAPP(settingsPackageName);
    }


}


