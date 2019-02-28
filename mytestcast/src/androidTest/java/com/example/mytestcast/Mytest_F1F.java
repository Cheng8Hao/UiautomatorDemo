package com.example.mytestcast;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.media.AudioManager;
import android.os.Environment;
import android.os.PowerManager;
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
public class Mytest_F1F {
    private static final String TAG = "Mytest_F1F";
    //获取测试包的Context
    Context testContext = InstrumentationRegistry.getContext();
    //获取被测应用的Context
    static Context applicationContext = InstrumentationRegistry.getTargetContext().getApplicationContext();
    private static UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
    int displayHeight = mDevice.getDisplayHeight();
    int displayWidth = mDevice.getDisplayWidth();
    private static final String setscreen_off_timeout_10mins = "settings put system screen_off_timeout 610000";
    private static final String setscreen_off_timeout_2s = "settings put system screen_off_timeout 2000";
    private static final String setscreen_off_timeout_2mins = "settings put system screen_off_timeout 120000";
    private static final String setbrightness_0 = "settings put  system screen_brightness 0";
    private static final String setbrightness_255 = "settings put  system screen_brightness 255";
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
    private static final String QQPackageName = "com.tencent.mobileqq";
    private static final String PhonePackageName = "com.android.phone";
    private static final String uiautoPackageName = "com.example.MyTest";

    private static final String weiboPackageName = "com.sina.weibo";
    private static final String angrybirdoPackageName = "com.rovio.angrybirdsseasons";
    private static final String weixinPackageName = "com.rovio.angrybirdsseasons";

    private static final String FILE_NAME = "/uiautomatorTest.txt";
    private static final int timeOut = 5000;//5秒
    private static final int fifteenminstime = 15 * 60 * 1000;//15分钟
    private static final int tenseconds = 10 * 1000;//10秒
    private static final int thirtyseconds = 30 * 1000;//10秒
    private static final int tenmins = 30 * 1000;//10 * 60 * 1000;//10分钟
    private static final int onemins = 60 * 1000;//1分钟
    private static int testcaseNo = 0; //测试case的序列号
    private static final String configFILE_NAME = "/myconfig.xml";

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
        getInstrumentation().getUiAutomation().executeShellCommand("pm grant " + InstrumentationRegistry.getTargetContext().getPackageName() + " android.permission.MODIFY_AUDIO_SETTINGS");
        //获取包名
        getlistpackages();
        //每次开始测试前先删除测试报告
        deletefile();
        //设置手机熄灭屏幕时间，除去熄灭屏幕解锁方式
        //setDisplayTimeandScreenLock();
        //setMaxStreamVolume();
        mDevice.executeShellCommand(setbrightness_255);
        // 序号	测试项描述	测试开始时间	电池电压（mV）	电池电量(%)	测试结束时间	电池电压（mV）	电池电量(%) 单项测试时长（mins）	备注
        write("SerialNumber" + "\t" + "Description" + "\t" + "StartTime" + "\t" + "Voltage(mV)" + "\t" + "Electricity(%)" + "\t" + "EndTime" + "\t" + "Voltage(mV)" + "\t" + "Electricity(%)" + "\t" + "ItemTestTime(mins)" + "\t" + "Note" + "\t" + "\n");
        //测试开始前获取config文件信息
        getcongif();
    }

    @AfterClass
    public static void afterClass() {
        //测试项目结束时运行（仅一次），如卸载应用等
        Log.d(TAG, "afterClass: ");
        //不需要打开uiauto.apk了,关机可以接收到寡薄
        //startApp(uiautoPackageName,timeOut);
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


    //call 10mins
    @Test
    public void test_001() throws InterruptedException, UiObjectNotFoundException, IOException, RemoteException {
        PowerManager pm = (PowerManager) applicationContext.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wk = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
        mDevice.executeShellCommand(setscreen_off_timeout_2s);
        write(testcaseNo + "\t" + "Call test" + "\t" + getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t");
        int startTime = (int) System.currentTimeMillis();
        int i;
        wk.acquire();
        for (i = 0; i < call_count; i++) {
            //Log.d(TAG, "test_001: i===" + i);
            mDevice.executeShellCommand(dialCMD + CooperatingPhoneNum);
            //Log.d(TAG, "test_001: dialstart");
            setSpeakerphoneOn(true);
            sleep(call_times + 10000);
            mDevice.executeShellCommand(endCall);
            // Log.d(TAG, "test_001: endCall");
        }
        wk.release();
        int endTime = (int) System.currentTimeMillis();
        int testTime = calculateTime(endTime, startTime);    //((endTime - startTime) / 1000) / 60;
        write(getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t" + testTime + "\t" + "test finish" + "\t" + "\n");
    }

    //play games angary bird
    @Test
    public void test_002() throws InterruptedException, UiObjectNotFoundException, IOException {
        startApp(angrybirdoPackageName,timeOut);
        sleep(30000);
        mDevice.click(displayWidth/2,displayHeight/2);
        sleep(5000);
        mDevice.pressBack();
        sleep(5000);
        mDevice.swipe(displayWidth/2,displayHeight/2,displayWidth/4,displayHeight/2,displayHeight/8);
        sleep(5000);
        mDevice.swipe(displayWidth/2,displayHeight/2,displayWidth/4,displayHeight/2,displayHeight/8);
    }

    //WeChat
    @Test
    public void test_003() throws InterruptedException, UiObjectNotFoundException, IOException {
    }


    //SMS
    @Test
    public void test_004() throws InterruptedException, UiObjectNotFoundException, IOException {
        write(testcaseNo + "\t" + "SMS test" + "\t" + getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t");
        startApp(MessagePackageName, timeOut); //启动app

        UiObject Start_chat = new UiObject(new UiSelector().text("Start chat"));
        Start_chat.clickAndWaitForNewWindow();
        String CooperatingPhoneNum = "18557535936";//CooperatingPhoneNum;

        for (int i = 0; i < CooperatingPhoneNum.length(); i++) {
            char c = CooperatingPhoneNum.charAt(i);
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
        mDevice.pressEnter();
        for (int sendSMSCount = 0; sendSMSCount < 5; sendSMSCount++) {
            String smsContent = "testContent";//CooperatingPhoneNum;
            for (int i = 0; i < smsContent.length(); i++) {
                char c = smsContent.charAt(i);
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
            UiObject send = new UiObject(new UiSelector().resourceId("com.google.android.apps.messaging:id/send_message_button_icon"));
            send.clickAndWaitForNewWindow();
            sleep(onemins);//1mins
        }
    }

    //QQ
    @Test
    public void test_005() throws InterruptedException, UiObjectNotFoundException, IOException {
        startApp(QQPackageName, timeOut);
        sleep(5000);
        //qq因为有验证码，需要在测试前人工登陆，不然测试会中断,QQ得跟换以前的版本！不然获取不到控件
        UiObject contact = new UiObject(new UiSelector().text("联系人"));
        contact.clickAndWaitForNewWindow();
        mDevice.click(displayWidth/2,displayHeight-2);
        UiObject myfriend = new UiObject(new UiSelector().text("我的好友"));
        myfriend.clickAndWaitForNewWindow();
        UiObject babyQ = new UiObject(new UiSelector().text("babyQ"));
        babyQ.clickAndWaitForNewWindow();
        UiObject sendmes = new UiObject(new UiSelector().text("发消息"));
        sendmes.clickAndWaitForNewWindow();
        int endTime;
        while (true) {
            Log.d(TAG, "test_006: while true");
            UiObject input = new UiObject(new UiSelector().resourceId("com.tencent.qqlite:id/input"));
            input.setText("hello?");
            sleep(5000);
            UiObject send = new UiObject(new UiSelector().resourceId("com.tencent.qqlite:id/fun_btn"));
            send.clickAndWaitForNewWindow();
            sleep(5000);
            /*endTime = (int) System.currentTimeMillis();
            Log.d(TAG, "test_006: startTime=" + startTime);
            Log.d(TAG, "test_006: endTime=" + endTime);
            int timegap = Math.abs(endTime - startTime);
            Log.d(TAG, "test_006: timegap=" + timegap);
            if (timegap >= qqtalk_time) {
                Log.d(TAG, "test_006: break");
                break;
            }*/
            break;
        }
        closeAPP(QQPackageName);

    }


    //Email
    @Test
    public void test_006() throws InterruptedException, UiObjectNotFoundException, IOException {
    }


    //web
    @Test
    public void test_007() throws InterruptedException, UiObjectNotFoundException, IOException {
        write(testcaseNo + "\t" + "Browser test" + "\t" + getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t");
        int startTime = (int) System.currentTimeMillis();
        startApp(ChormePackageName, timeOut); //启动app
        //得到浏览器中的网页输入框
        int testTime;
        int endTime = -1;
        UiObject edit = new UiObject(new UiSelector().className("android.widget.EditText"));
        edit.clickAndWaitForNewWindow();
        //edit.clearTextField();
        mDevice.pressDelete();
        edit.setText("www.163.com");
        mDevice.pressEnter();//回车进行浏览，在部分手机不支持回车浏览，可以使用上面的方式得到浏览按钮在点击进行浏览
        sleep(10000);
        while (true) {
            sleep(10000);
            mDevice.swipe(displayWidth / 2, displayHeight / 4, displayWidth / 2, displayHeight / 4 * 3, displayHeight/50);
            endTime = (int) System.currentTimeMillis();
            if (Math.abs(endTime - startTime) > openWebTest_time) {
                Log.d(TAG, "case1:startTime " + startTime);
                Log.d(TAG, "case1: endTime=" + endTime);
                break;
            }
        }
        testTime = calculateTime(endTime, startTime);    //((endTime - startTime) / 1000) / 60;   //分钟
        closeAPP(ChormePackageName);
        write(getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t" + testTime + "\t" + "test finish" + "\t" + "\n");
    }

    //    Weibo
    @Test
    public void test_008() throws InterruptedException, UiObjectNotFoundException, IOException {
        startApp(weiboPackageName, timeOut);
        write(testcaseNo + "\t" + "Weibo test" + "\t" + getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t");
        int startTime = (int) System.currentTimeMillis();
        sleep(10000);
        UiObject Look_around = new UiObject(new UiSelector().text("Look around"));
        Look_around.clickAndWaitForNewWindow();
        while (true) {
            mDevice.swipe(displayWidth / 2, displayHeight / 4 * 3, displayWidth / 2, displayHeight / 4, displayHeight / 5);
            int endTime = (int) System.currentTimeMillis();
            if (endTime - startTime > 60000) {
                break;
            }
        }
        int endTime = (int) System.currentTimeMillis();
        int testTime = calculateTime(endTime, startTime);    //((endTime - startTime) / 1000) / 60;
        closeAPP(weiboPackageName);
        write(getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t" + testTime + "\t" + "test finish" + "\t" + "\n");

    }

    //music
    @Test
    public void test_009() throws InterruptedException, UiObjectNotFoundException, IOException {
        //使用扬声器外放，即使已经插入耳机
        AudioManager mAudioManager = (AudioManager) applicationContext.getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        Log.d(TAG, "test_006: maxVolume=" + maxVolume);
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0); //音量调到最大
        mAudioManager.setMicrophoneMute(false);
        mAudioManager.setSpeakerphoneOn(false);
        mAudioManager.setMode(AudioManager.STREAM_MUSIC);
        //play music begin
        startApp(MusicPackageName, timeOut);
        sleep(10000);
        if (MusicPackageName.equals(googledMusicPackageName)) {
            Log.d(TAG, "test_006: open googledMusic ");
            //左滑
            mDevice.swipe(displayWidth - 1, displayHeight / 2, displayWidth / 3, displayHeight / 2, displayWidth / 9);
            mDevice.swipe(displayWidth - 1, displayHeight / 2, displayWidth / 3, displayHeight / 2, displayWidth / 9);
            UiObject Shuffle_all = new UiObject(new UiSelector().text("Shuffle all"));
            Shuffle_all.clickAndWaitForNewWindow();
        }

        /*if (MusicPackageName.equals(googledMusicPackageName)) {
            Log.d(TAG, "test_006: open googledMusic ");
            UiObject skip = new UiObject(new UiSelector().resourceId("com.google.android.music:id/skip_button"));
            skip.clickAndWaitForNewWindow();
            //左滑
            mDevice.swipe(displayWidth - 10, displayHeight / 2, displayWidth / 3, displayHeight / 2, displayWidth / 9);
            mDevice.swipe(displayWidth - 10, displayHeight / 2, displayWidth / 3, displayHeight / 2, displayWidth / 9);
            UiObject Shuffle_all = new UiObject(new UiSelector().text("Shuffle all"));
            Shuffle_all.clickAndWaitForNewWindow();
            UiObject song = new UiObject(new UiSelector().text("Nana-Lonely"));
            song.clickAndWaitForNewWindow();
            UiObject repeat = new UiObject(new UiSelector().resourceId("com.google.android.music:id/repeat"));
            repeat.clickAndWaitForNewWindow();
        }*/
        else if (MusicPackageName.equals(androidMusicPackageName)) {
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
        sleep(2000);
    }

    //play video
    @Test
    public void test_010() throws InterruptedException, UiObjectNotFoundException, IOException {
        {
            write(testcaseNo + "\t" + "PlayVideo test" + "\t" + getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t");
            int startTime = (int) System.currentTimeMillis();
            startApp(FilesPackageName, timeOut);

            //使用扬声器外放，即使已经插入耳机   耳机、外放都有声音
            AudioManager mAudioManager = (AudioManager) applicationContext.getSystemService(Context.AUDIO_SERVICE);
            int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            Log.d(TAG, "test_006: maxVolume=" + maxVolume);
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0); //音量调到最大
            mAudioManager.setMicrophoneMute(false);
            mAudioManager.setSpeakerphoneOn(true);
            mAudioManager.setMode(AudioManager.STREAM_MUSIC);

            if (FilesPackageName.contains("files")) {
                if (mDevice.hasObject(By.text("CONTINUE"))) {
                    Log.d(TAG, "test_009: into CONTINUE");
                    UiObject continueButton = new UiObject(new UiSelector().text("CONTINUE"));
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
                    Log.d(TAG, "test_009: playVideo_time / fifteenminstime ===" + (playVideo_time / fifteenminstime));
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
            int testTime = calculateTime(endTime, startTime);    //((endTime - startTime) / 1000) / 60;
            write(getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t" + testTime + "\t" + "test finish" + "\t" + "\n");

        }
    }

    //take picture
    @Test
    public void test_011() throws IOException, UiObjectNotFoundException, InterruptedException {
        write(testcaseNo + "\t" + "Camera test" + "\t" + getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t");
        int startTime = (int) System.currentTimeMillis();
        startApp(CameraPackageName, timeOut);
        //后置摄像头拍10张
        UiObject backshutterButton = new UiObject(new UiSelector().resourceId("com.mediatek.camera:id/shutter_image"));
        if (backshutterButton.exists()) {
            int i;
            for (i = 0; i < takePicture_count; i++) {
                backshutterButton.clickAndWaitForNewWindow();
                sleep(5000);
                UiObject thumbnail = new UiObject(new UiSelector().resourceId("com.mediatek.camera:id/thumbnail"));
                thumbnail.clickAndWaitForNewWindow();
                sleep(5000);
                mDevice.pressBack();
                sleep(60000);
            }
        } else {
            UiObject backshutterButton2 = new UiObject(new UiSelector().resourceId("com.mediatek.camera:id/shutter_button"));
            if (backshutterButton2.exists()) {
                int i;
                for (i = 0; i < takePicture_count; i++) {
                    backshutterButton2.clickAndWaitForNewWindow();
                    sleep(5000);
                    UiObject thumbnail = new UiObject(new UiSelector().resourceId("com.mediatek.camera:id/thumbnail"));
                    thumbnail.clickAndWaitForNewWindow();
                    sleep(5000);
                    mDevice.pressBack();
                    sleep(60000);
                }
            }
        }
        //如果有前置摄像头的话,切换摄像头，前置摄像头拍10张，
       /* UiObject switcherButton = new UiObject(new UiSelector().resourceId("com.mediatek.camera:id/camera_switcher"));
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
        }*/
        int endTime = (int) System.currentTimeMillis();
        int testTime = calculateTime(endTime, startTime);    //((endTime - startTime) / 1000) / 60;
        closeAPP(CameraPackageName);
        write(getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t" + testTime + "\t" + "test finish" + "\t" + "\n");

    }

    //take video
    @Test
    public void test_012() throws InterruptedException, UiObjectNotFoundException, IOException {
        write(testcaseNo + "\t" + "TakeVideo test" + "\t" + getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t");
        int startTime = (int) System.currentTimeMillis();
        startApp(CameraPackageName, timeOut);
        //A15，F1
        UiObject videoshutterButton1 = new UiObject(new UiSelector().resourceId("com.mediatek.camera:id/shutter_button_video"));
        if (videoshutterButton1.exists()) {
            videoshutterButton1.clickAndWaitForNewWindow();
            sleep(takeVideo_time);
            videoshutterButton1.clickAndWaitForNewWindow();
            //2019-02-27 begin
            UiObject thumbnail = new UiObject(new UiSelector().resourceId("com.mediatek.camera:id/thumbnail"));
            thumbnail.clickAndWaitForNewWindow();
            sleep(1000);

            UiObject play_button = new UiObject(new UiSelector().resourceId("com.google.android.apps.photos:id/photos_videoplayer_play_button"));
            play_button.clickAndWaitForNewWindow();
            sleep(takeVideo_time);
            //2019-02-27 end

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
                //2019-02-27 begin
                UiObject thumbnail = new UiObject(new UiSelector().resourceId("com.mediatek.camera:id/thumbnail"));
                thumbnail.clickAndWaitForNewWindow();
                sleep(1000);

                UiObject play_button = new UiObject(new UiSelector().resourceId("com.google.android.apps.photos:id/photos_videoplayer_play_button"));
                play_button.clickAndWaitForNewWindow();
                sleep(takeVideo_time);
                //2019-02-27 end
            }
        }

        int endTime = (int) System.currentTimeMillis();
        int testTime = calculateTime(endTime, startTime);    //((endTime - startTime) / 1000) / 60;//mins
        closeAPP(CameraPackageName);
        write(getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t" + testTime + "\t" + "test finish" + "\t" + "\n");
    }

    //idle
    @Test
    public void test_013() throws InterruptedException, UiObjectNotFoundException, IOException {
        //mDevice.executeShellCommand(setscreen_off_timeout_10mins);
        int startTime = (int) System.currentTimeMillis();
        write(testcaseNo + "\t" + "Boot Then Standby" + "\t" + getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t");
        PowerManager pm = (PowerManager) applicationContext.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wk = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
        wk.acquire();
        sleep(bootThenStandby_time);
        wk.release();
        int endTime = (int) System.currentTimeMillis();
        int testTime = calculateTime(endTime, startTime);    //((endTime - startTime) / 1000) / 60;
        Log.d(TAG, "test_013: testTime=" + testTime);
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
                                    "bootThenStandby_time" + bootThenStandby_time + "\n" +
                                            "CooperatingPhoneNum" + CooperatingPhoneNum + "\n" +
                                            "UTDPhoneNum" + UTDPhoneNum + "\n" +
                                            "targetGmail" + targetGmail + "\n" +
                                            "myGmailNumber" + myGmailNumber + "\n" +
                                            "mygmailpassword" + mygmailpassword + "\n" +
                                            "openWebTest_time" + openWebTest_time + "\n" +
                                            "qqtalk_time" + qqtalk_time + "\n" +
                                            "call_count" + call_count + "\n" +
                                            "call_times" + call_times + "\n" +
                                            "endCall_count" + endCall_count + "\n" +
                                            "endCall_times" + endCall_times + "\n" +
                                            "playMusic_time" + playMusic_time + "\n" +
                                            "takePicture_count" + takePicture_count + "\n" +
                                            "playVideo_time" + playVideo_time + "\n" +
                                            "playgames_time" + playgames_time + "\n" +
                                            "sendmail_count" + sendmail_count + "\n" +
                                            "takeVideo_time" + takeVideo_time + "\n"
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

    //打开settings，设置自动灭屏时间为15s，设置Screen lock为none
    public static void setDisplayTimeandScreenLock() throws IOException {
        startApp(settingsPackageName, timeOut);
        try {
           /* UiObject Display = new UiObject(new UiSelector().text("Display"));
            Display.clickAndWaitForNewWindow();
            UiObject Sleep = new UiObject(new UiSelector().text("Sleep"));
            Sleep.clickAndWaitForNewWindow();
            UiObject minutes = new UiObject(new UiSelector().text("15 seconds"));
            minutes.clickAndWaitForNewWindow();
            mDevice.pressBack();*/

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

    //计算时间间隔，单位是min
    public static int calculateTime(int starttime, int endtime) {
        return Math.abs(endtime - starttime) / 1000 / 60;
    }

    private void setSpeakerphoneOn(boolean on) {
        AudioManager audioManager = (AudioManager) applicationContext.getSystemService(Context.AUDIO_SERVICE);
        if (on) {
            audioManager.setSpeakerphoneOn(true);
        } else {
            audioManager.setSpeakerphoneOn(false);//关闭扬声器
            audioManager.setRouting(AudioManager.MODE_NORMAL, AudioManager.ROUTE_EARPIECE, AudioManager.ROUTE_ALL);
            //把声音设定成Earpiece（听筒）出来，设定为正在通话中
            audioManager.setMode(AudioManager.MODE_IN_CALL);
        }
    }

    public static void setMaxStreamVolume() {
        AudioManager mAudioManager = (AudioManager) applicationContext.getSystemService(Context.AUDIO_SERVICE);
        int streamMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, streamMaxVolume, 1);
    }

}


