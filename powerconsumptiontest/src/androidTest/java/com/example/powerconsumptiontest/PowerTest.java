package com.example.powerconsumptiontest;

import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Switch;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;


/**
 * 作者    HuangShun
 * 时间    11/26/18 2:01 PM
 * 文件    UIAuto
 * 描述
 */
@RunWith(AndroidJUnit4.class)
public class PowerTest {
    String TAG="HuangShun";
    //
    UiDevice mDevice;
    @Before
    public void before() {
        //测试开始前运行（每个@Test运行前都会运行一次），如打开应用等
        Log.d(TAG, "before: ");
        //获取设备用例
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
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

    }

    private void startAPP(String sPackageName){
        Log.d(TAG, "startAPP:"+sPackageName);
        Context mContext = InstrumentationRegistry.getContext();
        Intent myIntent = mContext.getPackageManager().getLaunchIntentForPackage(sPackageName);  //通过Intent启动app
        mContext.startActivity(myIntent);


    }

    private void closeAPP(String sPackageName){
        Log.i(TAG, "closeAPP: "+sPackageName);
        try {//am force-stop
            mDevice.executeShellCommand("pm clear "+sPackageName);//通过命令行关闭app
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startAPP(UiDevice uiDevice,String sPackageName, String sLaunchActivity){
        try {
            uiDevice.executeShellCommand("am start -n "+sPackageName+"/"+sLaunchActivity);//通过命令行启动app
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //com.android.settings   打开设置  前提准备
    @Test
    public void openSetting()throws  Exception{
        String packgeName ="com.android.settings";
        startAPP(packgeName);
        //Network & internet
        UiSelector wifi = new UiSelector().textContains("Network");
        UiObject obj_wifi =new UiObject(wifi);
        obj_wifi.clickAndWaitForNewWindow(3000);

    // 1  关闭wifi
        //com.android.settings:id/switchWidget
        UiObject off_obj=new UiObject(new UiSelector().resourceId("com.android.settings:id/switchWidget"));
        if(off_obj.getText().equals("ON")){
            //如果wifi时打开的就关闭
            off_obj.clickAndWaitForNewWindow(3000);
        }

    // 2 开启飞行模式
        UiObject air_off = new UiObject(new UiSelector().resourceId("android:id/switch_widget"));
        if(air_off.getText().equals("OFF")){
            air_off.click();
        }

        Thread.sleep(3000);


    // 3  关闭蓝牙
        //返回
        mDevice.pressBack();
        //Connected devices
        UiObject bule_obj = new UiObject(new UiSelector().text("Connected devices"));
        bule_obj.clickAndWaitForNewWindow(3000);
        UiObject off_blue=new UiObject(new UiSelector().resourceId("com.android.settings:id/switchWidget"));
        if(off_blue.getText().equals("ON")){
            //如果蓝牙是打开的就关闭蓝牙
            off_blue.clickAndWaitForNewWindow(3000);
        }
        mDevice.pressBack();
    // 4 关闭gps
        UiScrollable RecyclerView = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));
        RecyclerView.setMaxSearchSwipes(10);//设置最大可扫动次数
        UiObject Security = new UiObject(new UiSelector().text("Security & location"));
        RecyclerView.scrollIntoView(Security);
        Security.click();

        UiScrollable scroll=new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));
        scroll.setMaxSearchSwipes(10);
        UiObject location = new UiObject(new UiSelector().text("Location"));
        scroll.scrollIntoView(location);
        location.click();


        UiObject off_location = new UiObject(new UiSelector().resourceId("com.android.settings:id/switch_widget"));
        if(off_location.getText().equals("ON")){
            //如果gps打开就关闭
            off_location.clickAndWaitForNewWindow(3000);
        }

        mDevice.pressBack();
        mDevice.pressBack();

    // 5 进入无障碍 关闭屏幕自动旋转
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
        if(checked){
            relativeLayout.click();
        }
        Thread.sleep(3000);
        mDevice.pressBack();
        closeAPP(packgeName);
    }

}
