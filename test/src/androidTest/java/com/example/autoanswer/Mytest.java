package com.example.autoanswer;

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
    Context applicationContext = InstrumentationRegistry.getTargetContext().getApplicationContext();
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
    private static final String QQPackageName = "com.tencent.qqlite";
    private static final String PhonePackageName = "com.android.phone";
    private static final String uiautoPackageName = "com.example.MyTest";
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
        // 序号	测试项描述	测试开始时间	电池电压（mV）	电池电量(%)	测试结束时间	电池电压（mV）	电池电量(%) 单项测试时长（mins）	备注
        write("SerialNumber" + "\t" + "Description" + "\t" + "StartTime" + "\t" + "Voltage(mV)" + "\t" + "Electricity(%)" + "\t" + "EndTime" + "\t" + "Voltage(mV)" + "\t" + "Electricity(%)" + "\t" + "ItemTestTime(mins)" + "\t" + "Note" + "\t" + "\n");
        //测试开始前获取config文件信息
        getcongif();
    }

    @AfterClass
    public static void afterClass() {
        //测试项目结束时运行（仅一次），如卸载应用等
        Log.d(TAG, "afterClass: ");
        startApp(uiautoPackageName, timeOut);
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


    //call
    @Test
    public void test_002() throws InterruptedException, UiObjectNotFoundException, IOException, RemoteException {
        PowerManager pm = (PowerManager) applicationContext.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wk = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
        write(testcaseNo + "\t" + "Call test" + "\t" + getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t");
        int startTime = (int) System.currentTimeMillis();
        int i;
        wk.acquire();
        for (i = 0; i < 2; i++) {
            Log.d(TAG, "test_002: i===" + i);
            mDevice.executeShellCommand(dialCMD + "10010");//CooperatingPhoneNum);
            setSpeakerphoneOn(true);
            sleep(10000);
            mDevice.executeShellCommand(endCall);
        }
        wk.release();
    }

    // playmusic
    @Test
    public void test_006() throws InterruptedException, UiObjectNotFoundException, IOException {
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
        sleep(5000);
        if (MusicPackageName.equals(googledMusicPackageName)) {
            Log.d(TAG, "test_006: open googledMusic ");
            //左滑
            mDevice.swipe(displayWidth - 1, displayHeight / 2, displayWidth / 3, displayHeight / 2, displayWidth / 9);
            mDevice.swipe(displayWidth - 1, displayHeight / 2, displayWidth / 3, displayHeight / 2, displayWidth / 9);
            UiObject Shuffle_all = new UiObject(new UiSelector().text("Shuffle all"));
            Shuffle_all.clickAndWaitForNewWindow();
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
        sleep(25000);
        closeAPP(MusicPackageName);
    }

    //_playvideo
    //adb push 720.mp4 sdcard/   是push到手机内部sd卡上，不是外置sd卡上;
    // 而且需要重启手机才能在filesGO中找到文件
    @Test
    public void test_009() throws IOException, UiObjectNotFoundException, InterruptedException {
        write(testcaseNo + "\t" + "PlayVideo test" + "\t" + getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t");
        int startTime = (int) System.currentTimeMillis();
        startApp(FilesPackageName, timeOut);
        //使用扬声器外放，即使已经插入耳机   耳机、外放都有声音
        AudioManager mAudioManager = (AudioManager) applicationContext.getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
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
            for (i = 0; i < 2; i++) {
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
                sleep(30000);
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
            for (i = 0; i < 2; i++) {
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
                sleep(30000);
                mDevice.pressBack();
            }
            closeAPP(FilesPackageName);
        }
        int endTime = (int) System.currentTimeMillis();
        int testTime = calculateTime(endTime, startTime);    //((endTime - startTime) / 1000) / 60;
        write(getTimeInfo() + "\t" + getBatterryVoltage() + "\t" + getBatterryElectric() + "\t" + testTime + "\t" + "test finish" + "\t" + "\n");

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
            UiObject Display = new UiObject(new UiSelector().text("Display"));
            Display.clickAndWaitForNewWindow();
            UiObject Sleep = new UiObject(new UiSelector().text("Sleep"));
            Sleep.clickAndWaitForNewWindow();
            UiObject minutes = new UiObject(new UiSelector().text("15 seconds"));
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

    //计算时间间隔，单位是min
    public static int calculateTime(int starttime, int endtime) {
        return Math.abs(endtime - starttime) / 1000 / 60;
    }


}


