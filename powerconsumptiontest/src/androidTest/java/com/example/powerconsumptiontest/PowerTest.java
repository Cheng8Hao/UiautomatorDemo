package com.example.powerconsumptiontest;

import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.media.AudioManager;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.UiWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Switch;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static java.lang.Thread.sleep;

@RunWith(AndroidJUnit4.class)
public class PowerTest {
    private static final String settingsPackgeName = "com.android.settings";
    private static final String soundrecorderPackgeName = "com.android.soundrecorder";
    private static final String ChormePackageName = "com.android.chrome";


    private static final String TAG = "PowerTest";
    //获取测试包的Context
    Context testContext = InstrumentationRegistry.getContext();
    //获取被测应用的Context
    Context applicationContext = InstrumentationRegistry.getTargetContext().getApplicationContext();
    private static UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
    int displayHeight = mDevice.getDisplayHeight();
    int displayWidth = mDevice.getDisplayWidth();
    public static int DefaultBrightness;
    private static final String CameraPackageName = "com.mediatek.camera";
    int timeOut = 3000;
    private static final String endCall = "input  keyevent  KEYCODE_ENDCALL";
    private static final String dialCMD = "am start -a android.intent.action.CALL tel:";
    private String CooperatingPhoneNum = "18067112583";
    AudioManager mAudioManager = (AudioManager) testContext.getSystemService(Context.AUDIO_SERVICE);
    private String setscreen_off_timeout_5mins = "settings put system screen_off_timeout 300000";
    private String FMPackgeName = "com.android.fmradio";

    @Before
    public void setUp() throws Exception {
    }

    @Before
    public void before() {
        //测试开始前运行（每个@Test运行前都会运行一次），如打开应用等
        Log.d(TAG, "before: ");
        //获取设备用例
        mDevice = UiDevice.getInstance(getInstrumentation());
        try {
            if (!mDevice.isScreenOn()) {
                Log.e(TAG, "isScreenOn==" + mDevice.isScreenOn());
                mDevice.wakeUp();
            }
            mDevice.pressHome();
        } catch (RemoteException e) {
            Log.e(TAG, "Device wakeUp exception!");
            e.printStackTrace();
        }
        //
        try {
            grantPremmison();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d(TAG, "registwatcher: ");
        MyWatcher myWatcher = new MyWatcher(mDevice);
        mDevice.registerWatcher("testwatcher", myWatcher);

    }

    //启动apk
    private void startApk(String sPackageName) {
        Log.d(TAG, "startApk:" + sPackageName);
        /*Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(sPackageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
        context.startActivity(intent);
        mDevice.wait(Until.hasObject(By.pkg(sPackageName).depth(0)), 5000);  // Wait for the app to appear*/
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(sPackageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    //启动apk
    private void startApk(UiDevice uiDevice, String sPackageName, String sLaunchActivity) {
        try {
            uiDevice.executeShellCommand("am start -n " + sPackageName + "/" + sLaunchActivity);//通过命令行启动app
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //关闭apk
    private void closeApk(String sPackageName) {
        Log.i(TAG, "closeApk: " + sPackageName);
        try {
            //mDevice.executeShellCommand("pm clear " + sPackageName);//通过命令行关闭app并清楚数据
            mDevice.executeShellCommand("am force-stop " + sPackageName);//通过命令行关闭app
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //关闭wifi
    @Test
    public void closeWifi() throws Exception {
        startApk(settingsPackgeName);
        //Network & internet
        UiSelector wifi = new UiSelector().textContains("Network");
        UiObject obj_wifi = new UiObject(wifi);
        obj_wifi.clickAndWaitForNewWindow(3000);
        // 1  关闭wifi
        UiObject off_obj = new UiObject(new UiSelector().resourceId("com.android.settings:id/switchWidget"));
        if (off_obj.getText().toUpperCase().equals("ON")) {
            //如果wifi时打开的就关闭
            off_obj.clickAndWaitForNewWindow(3000);
        }
        sleep(3000);
        closeApk(settingsPackgeName);
    }

    //打开wifi
    @Test
    public void openWifi() throws Exception {
        startApk(settingsPackgeName);
        //Network & internet
        UiSelector wifi = new UiSelector().textContains("Network");
        UiObject obj_wifi = new UiObject(wifi);
        obj_wifi.clickAndWaitForNewWindow(3000);
        // 1  关闭wifi
        UiObject off_obj = new UiObject(new UiSelector().resourceId("com.android.settings:id/switchWidget"));
        if (off_obj.getText().toUpperCase().equals("OFF")) {
            //如果wifi时打开的就关闭
            off_obj.clickAndWaitForNewWindow(3000);
        }
        sleep(3000);
        closeApk(settingsPackgeName);
    }

    //开启飞行模式
    @Test
    public void openAirPlane() throws Exception {
        startApk(settingsPackgeName);
        //Network & internet
        UiSelector wifi = new UiSelector().textContains("Network");
        UiObject obj_wifi = new UiObject(wifi);
        obj_wifi.clickAndWaitForNewWindow(3000);
        // 2 开启飞行模式
        UiObject air_off = new UiObject(new UiSelector().resourceId("android:id/switch_widget"));
        if (air_off.getText().toUpperCase().equals("OFF")) {
            air_off.click();
        }
        sleep(3000);
        closeApk(settingsPackgeName);

    }

    //开启飞行模式
    @Test
    public void closeAirPlane() throws Exception {
        startApk(settingsPackgeName);
        //Network & internet
        UiSelector wifi = new UiSelector().textContains("Network");
        UiObject obj_wifi = new UiObject(wifi);
        obj_wifi.clickAndWaitForNewWindow(3000);
        // 2 开启飞行模式
        UiObject air_off = new UiObject(new UiSelector().resourceId("android:id/switch_widget"));
        if (air_off.getText().toUpperCase().equals("ON")) {
            air_off.click();
        }
        sleep(3000);
        closeApk(settingsPackgeName);

    }

    //关闭蓝牙
    @Test
    public void closeBlueTooth() throws Exception {
        //Connected devices
        startApk(settingsPackgeName);
        UiObject bule_obj = new UiObject(new UiSelector().text("Connected devices"));
        bule_obj.clickAndWaitForNewWindow(3000);
        UiObject off_blue = new UiObject(new UiSelector().resourceId("com.android.settings:id/switchWidget"));
        if (off_blue.getText().toUpperCase().equals("ON")) {
            //如果蓝牙是打开的就关闭蓝牙
            off_blue.clickAndWaitForNewWindow(3000);
        }
        closeApk(settingsPackgeName);
    }

    //打开蓝牙
    @Test
    public void openBlueTooth() throws Exception {
        //Connected devices
        startApk(settingsPackgeName);
        UiObject bule_obj = new UiObject(new UiSelector().text("Connected devices"));
        bule_obj.clickAndWaitForNewWindow(3000);
        UiObject off_blue = new UiObject(new UiSelector().resourceId("com.android.settings:id/switchWidget"));
        if (off_blue.getText().toUpperCase().equals("OFF")) {
            //如果蓝牙是打开的就关闭蓝牙
            off_blue.clickAndWaitForNewWindow(3000);
        }
        closeApk(settingsPackgeName);
    }

    // 关闭gps
    @Test
    public void closeGPS() throws Exception {
        startApk(settingsPackgeName);
        UiScrollable RecyclerView = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));
        RecyclerView.setMaxSearchSwipes(10);//设置最大可扫动次数
        UiObject Security = new UiObject(new UiSelector().text("Security & location"));
        RecyclerView.scrollIntoView(Security);
        Security.click();

        UiScrollable scroll = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));
        scroll.setMaxSearchSwipes(10);
        UiObject location = new UiObject(new UiSelector().text("Location"));
        scroll.scrollIntoView(location);
        location.click();

        UiObject off_location = new UiObject(new UiSelector().resourceId("com.android.settings:id/switch_widget"));
        if (off_location.getText().toUpperCase().equals("ON")) {
            //如果gps打开就关闭
            off_location.clickAndWaitForNewWindow(3000);
        }
        closeApk(settingsPackgeName);
    }

    //打开gps
    @Test
    public void openGPS() throws Exception {
        startApk(settingsPackgeName);
        UiScrollable RecyclerView = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));
        RecyclerView.setMaxSearchSwipes(10);//设置最大可扫动次数
        UiObject Security = new UiObject(new UiSelector().text("Security & location"));
        RecyclerView.scrollIntoView(Security);
        Security.click();

        UiScrollable scroll = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));
        scroll.setMaxSearchSwipes(10);
        UiObject location = new UiObject(new UiSelector().text("Location"));
        scroll.scrollIntoView(location);
        location.click();

        UiObject off_location = new UiObject(new UiSelector().resourceId("com.android.settings:id/switch_widget"));
        if (off_location.getText().toUpperCase().equals("OFF")) {
            //如果gps打开就关闭
            off_location.clickAndWaitForNewWindow(3000);
        }
        closeApk(settingsPackgeName);
    }

    //进入无障碍 打开屏幕自动旋转
    @Test
    public void openAutoRotateScreen() throws Exception {
        startApk(settingsPackgeName);
        UiScrollable recyclerView2 = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));
        recyclerView2.setMaxSearchSwipes(10);//设置最大可扫动次数
        UiObject acc_obj1 = new UiObject(new UiSelector().text("Accessibility"));
        recyclerView2.scrollIntoView(acc_obj1);
        acc_obj1.click();

        UiScrollable recyclerView3 = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));
        recyclerView3.setMaxSearchSwipes(10);//设置最大可扫动次数
        UiObject auto_obj = new UiObject(new UiSelector().textContains("Mono"));
        recyclerView3.scrollIntoView(auto_obj);

        UiObject2 relativeLayout = mDevice.findObject(By.clazz(RelativeLayout.class).hasChild(By.text("Auto-rotate screen")));
        UiObject2 uiObjectpa = relativeLayout.getParent();
        UiObject2 switchi = uiObjectpa.findObject(By.clazz(Switch.class));
        boolean checked = switchi.isChecked();
        if (!checked) {
            relativeLayout.click();
        }
        sleep(3000);
        closeApk(settingsPackgeName);
    }

    //进入无障碍 关闭屏幕自动旋转
    @Test
    public void closeAutoRotateScreen() throws Exception {
        startApk(settingsPackgeName);
        UiScrollable recyclerView2 = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));
        recyclerView2.setMaxSearchSwipes(10);//设置最大可扫动次数
        UiObject acc_obj1 = new UiObject(new UiSelector().text("Accessibility"));
        recyclerView2.scrollIntoView(acc_obj1);
        acc_obj1.click();

        UiScrollable recyclerView3 = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));
        recyclerView3.setMaxSearchSwipes(10);//设置最大可扫动次数
        UiObject auto_obj = new UiObject(new UiSelector().textContains("Mono"));
        recyclerView3.scrollIntoView(auto_obj);

        UiObject2 relativeLayout = mDevice.findObject(By.clazz(RelativeLayout.class).hasChild(By.text("Auto-rotate screen")));
        UiObject2 uiObjectpa = relativeLayout.getParent();
        UiObject2 switchi = uiObjectpa.findObject(By.clazz(Switch.class));
        boolean checked = switchi.isChecked();
        if (checked) {
            relativeLayout.click();
        }
        sleep(3000);
        closeApk(settingsPackgeName);
    }

    //打开手机自动调节亮度
    @Test
    public void openBrightnessAutomatic() throws IOException, InterruptedException {
        // mDevice.executeShellCommand("settings put  system def_screen_brightness_automatic_mode true");
        startApk(settingsPackgeName);
        mDevice.findObject(By.text("Display")).click();
        sleep(2000);
        UiObject2 object = mDevice.findObject(By.res("android:id/switch_widget"));
        if (object.getText().toUpperCase().equals("OFF")) {
            object.click();
        }
    }

    //关闭手机自动调节亮度
    @Test
    public void closeBrightnessAutomatic() throws IOException, InterruptedException {
        // mDevice.executeShellCommand("settings put  system def_screen_brightness_automatic_mode true");
        startApk(settingsPackgeName);
        mDevice.findObject(By.text("Display")).click();
        sleep(2000);
        UiObject2 object = mDevice.findObject(By.res("android:id/switch_widget"));
        if (object.getText().toUpperCase().equals("ON")) {
            object.click();
        }
    }

    //获取手机默认亮度
    public int getDefaultBrightness() throws Exception {
        String s = mDevice.executeShellCommand("settings get  system screen_brightness");
        Log.d(TAG, "saveDefaultBrightness: s==" + s);
        DefaultBrightness = Integer.parseInt(s.trim());
        Log.d(TAG, "saveDefaultBrightness: defaultBrightness==" + DefaultBrightness);
        return DefaultBrightness;
    }

    //设置手机最大亮度
    @Test
    public void setMaxBrightness() throws Exception {
        mDevice.executeShellCommand("settings get  system screen_brightness 255");
    }

    //灭屏
    @Test
    public void sreenOff() throws Exception {
        mDevice.sleep();
    }

    //亮屏
    @Test
    public void sreenON() throws Exception {
        mDevice.wakeUp();
    }

    //单卡开启数据流量
    @Test
    public void openData() throws Exception {
        startApk(settingsPackgeName);
        UiObject Network_obj = new UiObject(new UiSelector().textContains("Network"));
        Network_obj.clickAndWaitForNewWindow();
        UiObject Datausage_obj = new UiObject(new UiSelector().text("Data usage"));
        Datausage_obj.clickAndWaitForNewWindow();
        UiObject data_off = new UiObject(new UiSelector().resourceId("android:id/switch_widget"));
        if (data_off.getText().toUpperCase().equals("OFF")) {
            data_off.clickAndWaitForNewWindow();
        }
    }

    //单卡关闭数据流量
    @Test
    public void closeData() throws Exception {
        startApk(settingsPackgeName);
        UiObject Network_obj = new UiObject(new UiSelector().textContains("Network"));
        Network_obj.clickAndWaitForNewWindow();
        UiObject Datausage_obj = new UiObject(new UiSelector().text("Data usage"));
        Datausage_obj.clickAndWaitForNewWindow();
        UiObject data_off = new UiObject(new UiSelector().resourceId("android:id/switch_widget"));
        if (data_off.getText().toUpperCase().equals("ON")) {
            data_off.clickAndWaitForNewWindow();
        }
    }

    //清除最近任务
    @Test
    public void closeRecent() throws Exception {
        mDevice.pressRecentApps();
        UiObject uiObject = new UiObject(new UiSelector().className(Button.class));
        uiObject.clickAndWaitForNewWindow();
    }

    //拨打配合机电话
    @Test
    public void dialNumber() throws Exception {
        mDevice.executeShellCommand(dialCMD + CooperatingPhoneNum);
    }

    //挂断电话
    @Test
    public void hangUp() throws Exception {
        mDevice.executeShellCommand(endCall);
    }

    //获取MAX音量
    @Test
    public void getStreamMaxVolume() {
        int streamMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        Log.d(TAG, "getStreamVolume: streamMaxVolume==" + streamMaxVolume);
    }

    //获取当前音量
    @Test
    public void getStreamVolume() {
        int streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        Log.d(TAG, "getStreamVolume: streamVolume==" + streamVolume);
    }

    //设置MAX音量
    @Test
    public void setMaxStreamVolume() {
        AudioManager mAudioManager = (AudioManager) applicationContext.getSystemService(Context.AUDIO_SERVICE);
        int streamMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, streamMaxVolume, 1);
    }

    //设置4/7音量
    @Test
    public void set47thStreamVolume() {
        AudioManager mAudioManager = (AudioManager) applicationContext.getSystemService(Context.AUDIO_SERVICE);
        int streamMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int tartgetVolume = streamMaxVolume / 7 * 4;
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, tartgetVolume, 1);
    }

    //设置自动灭屏5mins
    @Test
    public void setscreen_off_timeout_5mins() {
        try {
            mDevice.executeShellCommand(setscreen_off_timeout_5mins);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //打开录音apk，并录音
    @Test
    public void startRecord() throws UiObjectNotFoundException, InterruptedException, RemoteException {
        startApk(soundrecorderPackgeName);
        sleep(5000);
        UiObject uiObject = new UiObject(new UiSelector().resourceId("com.android.soundrecorder:id/recordButton"));
        uiObject.clickAndWaitForNewWindow();

        if (mDevice.hasObject(By.text("ALLOW"))) {
            Log.d(TAG, "startRecord: into if ok 进不来的！");
            UiObject ALLOW = new UiObject(new UiSelector().text("ALLOW"));
            ALLOW.clickAndWaitForNewWindow();
        }
        //mDevice.sleep();

    }

    //打开录音apk
    @Test
    public void openSoundRecord() throws UiObjectNotFoundException, InterruptedException, RemoteException {
        startApk(soundrecorderPackgeName);
        sleep(5000);
        mDevice.sleep();
    }


    //获取权限
    public void grantPremmison() throws Exception {
        Log.d(TAG, "grantPremmison: ");
        getInstrumentation().getUiAutomation().executeShellCommand("pm grant " + InstrumentationRegistry.getTargetContext().getPackageName() + " android.permission.MODIFY_AUDIO_SETTINGS");
        //getInstrumentation().getUiAutomation().executeShellCommand("pm grant " + InstrumentationRegistry.getTargetContext().getPackageName() + " android.permission.WRITE_SETTINGS");
        //getInstrumentation().getUiAutomation().executeShellCommand("pm grant " + testContext.getPackageName() + " android.permission.WRITE_SETTINGS");
    }

    //后置摄像头 预览界面
    @Test
    public void openBackCameraPicture() throws IOException, InterruptedException, UiObjectNotFoundException {
        startApk(CameraPackageName);
    }

    //前置摄像头 预览界面
    @Test
    public void openFrontCameraPicture() throws IOException, InterruptedException, UiObjectNotFoundException {
        startApk(CameraPackageName);
        sleep(5000);
        //如果有前置摄像头的话,切换摄像头
        UiObject switcherButton = new UiObject(new UiSelector().resourceId("com.mediatek.camera:id/camera_switcher"));
        if (switcherButton.exists()) {
            switcherButton.clickAndWaitForNewWindow();
        }
        UiObject switcherButton2 = new UiObject(new UiSelector().resourceId("com.mediatek.camera:id/onscreen_camera_picker"));
        if (switcherButton2.exists()) {
            switcherButton2.clickAndWaitForNewWindow();
        }
    }

    //后置摄像头拍照预览界面 灭屏待机
    @Test
    public void openBackCameraPictureThenScreenOff() throws IOException, InterruptedException, UiObjectNotFoundException, RemoteException {
        startApk(CameraPackageName);
        mDevice.sleep();
    }

    //后置摄像头 摄像
    @Test
    public void openBackCameraVideo() throws IOException, InterruptedException, UiObjectNotFoundException {
        startApk(CameraPackageName);
        sleep(5000);
        UiObject VideoButton3 = new UiObject(new UiSelector().textContains("Video"));
        if (VideoButton3.exists()) {
            Log.d(TAG, "openBackCameraVideo: VideoButton3.exists");
            VideoButton3.clickAndWaitForNewWindow();
        }
        UiObject VideoButton2 = new UiObject(new UiSelector().textContains("VIDEO"));
        if (VideoButton2.exists()) {
            Log.d(TAG, "openBackCameraVideo: VideoButton2.exists");
            VideoButton2.clickAndWaitForNewWindow();
        }

        UiObject videoshutterButton1 = new UiObject(new UiSelector().resourceId("com.mediatek.camera:id/shutter_button_video"));
        if (videoshutterButton1.exists()) {
            videoshutterButton1.clickAndWaitForNewWindow();
        }

        UiObject VideoButton = new UiObject(new UiSelector().textContains("video"));
        if (VideoButton.exists()) {
            VideoButton.clickAndWaitForNewWindow();
            UiObject videoshutterButton = new UiObject(new UiSelector().resourceId("com.mediatek.camera:id/shutter_image"));
            if (videoshutterButton.exists()) {
                videoshutterButton.clickAndWaitForNewWindow();
            }
        }
    }

    //前置摄像头 摄像
    @Test
    public void openFrontCameraVideo() throws IOException, InterruptedException, UiObjectNotFoundException {
        startApk(CameraPackageName);
        sleep(5000);
        //切换摄像头
        UiObject switcherButton = new UiObject(new UiSelector().resourceId("com.mediatek.camera:id/camera_switcher"));
        if (switcherButton.exists()) {
            switcherButton.clickAndWaitForNewWindow();
        }
        UiObject switcherButton2 = new UiObject(new UiSelector().resourceId("com.mediatek.camera:id/onscreen_camera_picker"));
        if (switcherButton2.exists()) {
            switcherButton2.clickAndWaitForNewWindow();
        }
        //录像
        UiObject VideoButton3 = new UiObject(new UiSelector().textContains("Video"));
        if (VideoButton3.exists()) {
            Log.d(TAG, "openBackCameraVideo: VideoButton3.exists");
            VideoButton3.clickAndWaitForNewWindow();
        }
        UiObject VideoButton2 = new UiObject(new UiSelector().textContains("VIDEO"));
        if (VideoButton2.exists()) {
            Log.d(TAG, "openBackCameraVideo: VideoButton2.exists");
            VideoButton2.clickAndWaitForNewWindow();
        }

        UiObject videoshutterButton1 = new UiObject(new UiSelector().resourceId("com.mediatek.camera:id/shutter_button_video"));
        if (videoshutterButton1.exists()) {
            videoshutterButton1.clickAndWaitForNewWindow();
        }

        UiObject VideoButton = new UiObject(new UiSelector().textContains("video"));
        if (VideoButton.exists()) {
            VideoButton.clickAndWaitForNewWindow();
            UiObject videoshutterButton = new UiObject(new UiSelector().resourceId("com.mediatek.camera:id/shutter_image"));
            if (videoshutterButton.exists()) {
                videoshutterButton.clickAndWaitForNewWindow();
            }
        }
    }

    //TODO
    @Test
    public void openBackCameraVideoWithFlash() throws
            IOException, InterruptedException, UiObjectNotFoundException {
        startApk(CameraPackageName);
    }


    @Test
    public void openFrontCameraPVideo() throws
            IOException, InterruptedException, UiObjectNotFoundException {
        startApk(CameraPackageName);
        sleep(5000);
        //如果有前置摄像头的话,切换摄像头，前置摄像头拍10张，
        UiObject switcherButton = new UiObject(new UiSelector().resourceId("com.mediatek.camera:id/camera_switcher"));
        if (switcherButton.exists()) {
            switcherButton.clickAndWaitForNewWindow();
        }
        UiObject switcherButton2 = new UiObject(new UiSelector().resourceId("com.mediatek.camera:id/onscreen_camera_picker"));
        if (switcherButton2.exists()) {
            switcherButton2.clickAndWaitForNewWindow();
        }

        UiObject VideoButton = new UiObject(new UiSelector().textContains("Video"));
        if (VideoButton.exists()) {
            Log.d(TAG, "openBackCameraVideo: VideoButton.exists");
            VideoButton.clickAndWaitForNewWindow();
        }
        UiObject VideoButton2 = new UiObject(new UiSelector().textContains("VIDEO"));
        if (VideoButton2.exists()) {
            Log.d(TAG, "openBackCameraVideo: VideoButton2.exists");
            VideoButton2.clickAndWaitForNewWindow();
        }
    }


    //打开手电筒 先使用CameraManager关闭flashlight，再使用uiautomator打开flashlight
    @Test
    public void openTorch() throws
            RemoteException, InterruptedException, UiObjectNotFoundException {
        try {
            //获取CameraManager
            CameraManager mCameraManager = (CameraManager) this.applicationContext.getSystemService(Context.CAMERA_SERVICE);
            //获取当前手机所有摄像头设备ID
            String[] ids = mCameraManager.getCameraIdList();
            for (String id : ids) {
                CameraCharacteristics c = mCameraManager.getCameraCharacteristics(id);
                //查询该摄像头组件是否包含闪光灯
                Boolean flashAvailable = c.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                /* 获取相机面对的方向
                * CameraCharacteristics.LENS_FACING_FRONT 前置摄像头
                * CameraCharacteristics.LENS_FACING_BACK 后只摄像头
                * CameraCharacteristics.LENS_FACING_EXTERNAL 外部的摄像头*/
                Integer lensFacing = c.get(CameraCharacteristics.LENS_FACING);
                if (flashAvailable != null && flashAvailable
                        && lensFacing != null && lensFacing == CameraCharacteristics.LENS_FACING_BACK) {
                    //打开或关闭手电筒
                    mCameraManager.setTorchMode(id, false ? true : false);
                }
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        mDevice.openQuickSettings();
        if (mDevice.hasObject(By.text("Flashlight"))) {
            Log.d(TAG, "startRecord: into if ok 进不来的！");
            UiObject ALLOW = new UiObject(new UiSelector().text("Flashlight"));
            ALLOW.clickAndWaitForNewWindow();
        }
        mDevice.sleep();
    }

    //关闭手电筒
    @Test
    public void closeTorch() throws
            RemoteException, InterruptedException, UiObjectNotFoundException {
        try {
            //获取CameraManager
            CameraManager mCameraManager = (CameraManager) this.applicationContext.getSystemService(Context.CAMERA_SERVICE);
            //获取当前手机所有摄像头设备ID
            String[] ids = mCameraManager.getCameraIdList();
            for (String id : ids) {
                CameraCharacteristics c = mCameraManager.getCameraCharacteristics(id);
                //查询该摄像头组件是否包含闪光灯
                Boolean flashAvailable = c.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                /* 获取相机面对的方向
                * CameraCharacteristics.LENS_FACING_FRONT 前置摄像头
                * CameraCharacteristics.LENS_FACING_BACK 后只摄像头
                * CameraCharacteristics.LENS_FACING_EXTERNAL 外部的摄像头*/
                Integer lensFacing = c.get(CameraCharacteristics.LENS_FACING);
                if (flashAvailable != null && flashAvailable
                        && lensFacing != null && lensFacing == CameraCharacteristics.LENS_FACING_BACK) {
                    //打开或关闭手电筒
                    mCameraManager.setTorchMode(id, false ? true : false);
                }
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    //连接指定5Gwifi  帐号：test5G   密码：12345678
    @Test
    public void connect5GWifi() throws Exception {
        startApk(settingsPackgeName);
        // Network & internet
        UiObject Network = new UiObject(new UiSelector().textContains("Network"));
        Network.clickAndWaitForNewWindow(3000);
        // open wifi
        UiObject off_obj = new UiObject(new UiSelector().resourceId("com.android.settings:id/switchWidget"));
        if (off_obj.getText().toUpperCase().equals("OFF")) {
            //如果wifi是关闭的就打开
            off_obj.clickAndWaitForNewWindow(3000);
        }
        UiObject obj_wifi = new UiObject(new UiSelector().textContains("Wi"));
        obj_wifi.clickAndWaitForNewWindow();
        UiScrollable RecyclerView = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));
        RecyclerView.setMaxSearchSwipes(10);//设置最大可扫动次数
        UiObject Security = new UiObject(new UiSelector().text("test5G"));
        RecyclerView.scrollIntoView(Security);
        Security.click();
        UiObject2 object = mDevice.findObject(By.clazz("android.widget.EditText"));
        object.setText("12345678");
        UiObject2 CONNECT = mDevice.findObject(By.text("CONNECT"));
        CONNECT.click();
    }

    //断开连接上的wifi
    @Test
    public void disconnect5GWifi() throws Exception {
        startApk(settingsPackgeName);
        // Network & internet
        UiObject Network = new UiObject(new UiSelector().textContains("Network"));
        Network.clickAndWaitForNewWindow(3000);
        // open wifi
        UiObject off_obj = new UiObject(new UiSelector().resourceId("com.android.settings:id/switchWidget"));
        if (off_obj.getText().toUpperCase().equals("OFF")) {
            //如果wifi是关闭的就打开
            off_obj.clickAndWaitForNewWindow(3000);
        }
        UiObject obj_wifi = new UiObject(new UiSelector().textContains("Wi"));
        obj_wifi.clickAndWaitForNewWindow();
        UiScrollable RecyclerView = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));
        RecyclerView.setMaxSearchSwipes(10);//设置最大可扫动次数
        UiObject Security = new UiObject(new UiSelector().text("Connected"));
        RecyclerView.scrollIntoView(Security);
        Security.click();
        UiObject2 CONNECT = mDevice.findObject(By.text("Forget"));
        CONNECT.click();
    }

    //连接指定BT耳机  帐号：test5G   密码：12345678
    @Test
    public void connectBlueTooth() throws Exception {
        startApk(settingsPackgeName);
        UiObject bule_obj = new UiObject(new UiSelector().text("Connected devices"));
        bule_obj.clickAndWaitForNewWindow(3000);
        UiObject off_blue = new UiObject(new UiSelector().resourceId("com.android.settings:id/switchWidget"));
        if (off_blue.getText().toUpperCase().equals("OFF")) {
            //如果蓝牙是打开的就关闭蓝牙
            off_blue.clickAndWaitForNewWindow(3000);
        }
        UiObject Bluetooth = new UiObject(new UiSelector().text("Bluetooth"));
        Bluetooth.clickAndWaitForNewWindow();
        UiObject Pair = new UiObject(new UiSelector().text("Pair new device"));
        Pair.clickAndWaitForNewWindow();

        UiScrollable RecyclerView = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));
        RecyclerView.setMaxSearchSwipes(10);//设置最大可扫动次数
        UiObject Security = new UiObject(new UiSelector().text("指定蓝牙耳机名"));
        RecyclerView.scrollIntoView(Security);
        Security.click();

        UiObject Pair2 = new UiObject(new UiSelector().text("PAIR"));
        Pair2.clickAndWaitForNewWindow();

    }

    //打开FM.apk 啥也不干
    // 前提是插入了耳机
    @Test
    public void openFM() throws Exception {
        startApk(FMPackgeName);
        UiObject2 allow = mDevice.findObject(By.clazz("android.widget.Button").text("ALLOW"));
        allow.click();
        UiObject2 fm_headset = mDevice.findObject(By.res("com.android.fmradio:id/fm_headset"));
        boolean enabled = fm_headset.isEnabled();
        if (enabled) {
            UiObject2 play_button = mDevice.findObject(By.res("com.android.fmradio:id/play_button"));
            play_button.click();
        }

    }

    //打开FM.apk 使用耳机
    // 前提是插入了耳机
    @Test
    public void openFMUseHeadset() throws Exception {
        startApk(FMPackgeName);
        boolean allow = mDevice.hasObject(By.clazz("android.widget.Button").text("ALLOW"));
        if (allow) {
            UiObject2 allowobj = mDevice.findObject(By.clazz("android.widget.Button").text("ALLOW"));
            allowobj.click();
        }
        UiObject2 fm_headset = mDevice.findObject(By.res("com.android.fmradio:id/fm_headset"));
        boolean enabled = fm_headset.isEnabled();
        if (!enabled) {
            UiObject2 play_button = mDevice.findObject(By.res("com.android.fmradio:id/play_button"));
            play_button.click();
        }
    }

    //打开FM.apk 使用扬声器
    @Test
    public void openFMUseSpeaker() throws Exception {
        startApk(FMPackgeName);
        boolean allow = mDevice.hasObject(By.clazz("android.widget.Button").text("ALLOW"));
        if (allow) {
            UiObject2 allowobj = mDevice.findObject(By.clazz("android.widget.Button").text("ALLOW"));
            allowobj.click();
        }
        UiObject2 fm_headset = mDevice.findObject(By.res("com.android.fmradio:id/fm_headset"));
        boolean enabled = fm_headset.isEnabled();
        if (!enabled) {
            UiObject2 play_button = mDevice.findObject(By.res("com.android.fmradio:id/play_button"));
            play_button.click();
        }
        fm_headset.click();
        sleep(2000);
        boolean Speaker = mDevice.hasObject(By.text("Speaker"));
        if (Speaker) {
            UiObject2 Speakerobj = mDevice.findObject(By.text("Speaker"));
            Speakerobj.click();
        }
    }

    //打开Chorme.apk下载apk
    @Test
    public void openChromeThenDownloadApk() throws Exception {
        startApk(ChormePackageName);
        boolean ACCEPT = mDevice.hasObject(By.clazz("android.widget.Button").textContains("ACCEPT"));
        if (ACCEPT) {
            UiObject2 ACCEPTobj = mDevice.findObject(By.clazz("android.widget.Button").textContains("ACCEPT"));
            ACCEPTobj.click();
        }
        sleep(1000);
        boolean NEXT = mDevice.hasObject(By.clazz("android.widget.Button").text("NEXT"));
        if (NEXT) {
            UiObject2 NEXTobj = mDevice.findObject(By.clazz("android.widget.Button").text("NEXT"));
            NEXTobj.click();
        }
        sleep(1000);
        boolean NO_THANKS = mDevice.hasObject(By.clazz("android.widget.Button").text("NO THANKS"));
        if (NO_THANKS) {
            UiObject2 NO_THANKSobj = mDevice.findObject(By.clazz("android.widget.Button").text("NO THANKS"));
            NO_THANKSobj.click();
        }
        sleep(1000);
        boolean KEEPGOOGLE = mDevice.hasObject(By.clazz("android.widget.Button").text("KEEP GOOGLE"));
        if (KEEPGOOGLE) {
            UiObject2 KEEPGOOGLESobj = mDevice.findObject(By.clazz("android.widget.Button").text("KEEP GOOGLE"));
            KEEPGOOGLESobj.click();
        }
        sleep(1000);
        UiObject edit = new UiObject(new UiSelector().className("android.widget.EditText"));
        edit.clickAndWaitForNewWindow();
        mDevice.pressDelete();
        edit.setText("www.baidu.com");
        mDevice.pressEnter();
        sleep(3000);


        /*UiObject edit2 = new UiObject(new UiSelector().className("android.widget.Button"));
        Rect bounds = edit2.getBounds();
        int y = bounds.centerY();
        bounds.
        edit2.clickAndWaitForNewWindow();
        mDevice.pressDelete();
        edit2.setText("weixin");
        mDevice.pressEnter();*/
    }


    public class MyWatcher implements UiWatcher {
        private UiDevice mDevice;

        public MyWatcher(UiDevice device) {
            mDevice = device;
        }

        @Override
        public boolean checkForCondition() {
            Log.d(TAG, "checkForCondition: running!");
            //check ALLOW popwindow
            if (mDevice.hasObject(By.text("ALLOW"))) {
                Log.d(TAG, "checkForCondition: into allow");
                UiObject2 uiObject2 = mDevice.findObject(By.text("ALLOW"));
                uiObject2.click();
                return true;
            }
            return true;
        }
    }


}

