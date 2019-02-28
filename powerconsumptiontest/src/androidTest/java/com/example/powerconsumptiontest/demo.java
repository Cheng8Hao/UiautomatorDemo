package com.example.powerconsumptiontest;

/**
 * Created by chenghao on 18-12-28.
 */

import android.os.RemoteException;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

/**
 * Created by chenghao on 18-11-23.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class demo {
    private static final String TAG = "demo";
    private static UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
    //获取测试包的Context


    //测试开始前运行（每个@Test运行前都会运行一次），如打开应用等
    @Before
    public void before() {
        Log.d(TAG, "before: ");
        /*try {
            if (!mDevice.isScreenOn()) {
                Log.e(TAG, "isScreenOn==" + mDevice.isScreenOn());
                mDevice.wakeUp();
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Device wakeUp exception!");
            e.printStackTrace();
        }*/
    }

    @Test
    public void test_airplanMode_Open() {
        Log.d(TAG, "test_airplanMode_Open: ");
        getInstrumentation().getUiAutomation().executeShellCommand("settings put  global airplane_mode_on 1");
    }

    @Test
    public void test_airplanMode_Close() {
        Log.d(TAG, "test_airplanMode_Close: ");
        getInstrumentation().getUiAutomation().executeShellCommand("settings put  global airplane_mode_on 0");
    }

    @Test
    public void test_enableData() {
        Log.d(TAG, "test_enableData: ");
        getInstrumentation().getUiAutomation().executeShellCommand("svc data enable");
    }

    @Test
    public void test_disableData() {
        Log.d(TAG, "test_disableData: ");
        getInstrumentation().getUiAutomation().executeShellCommand("svc data disable");
    }

    @Test
    public void test_wakeUp() throws RemoteException {
        Log.d(TAG, "test_wakeUp: ");
        try {
            mDevice.wakeUp();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_sleep() throws RemoteException {
        Log.d(TAG, "test_sleep: ");
        try {
            mDevice.sleep();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_brightness() throws RemoteException {
        Log.d(TAG, "test_brightness: ");
    }

}

