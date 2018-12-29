package com.example.powerconsumptiontest;

/**
 * Created by chenghao on 18-12-28.
 */

import android.os.RemoteException;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Switch;

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

    @Test
    public void test_001() {
        try {
            mDevice.wakeUp();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        getInstrumentation().getUiAutomation().executeShellCommand("svc wifi disable");
    }

    @Test
    public void test_002() {
        try {
            mDevice.sleep();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_003() {
        try {
            mDevice.wakeUp();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

       /* UiObject2 ui = mDevice.findObject(By.clazz(RelativeLayout.class).hasChild(By.text("Turn mute")));
        UiObject2 uiObjectpa = ui.getParent();
        UiObject2 switchi = uiObjectpa.findObject(By.clazz(Switch.class));
        boolean checked = switchi.isChecked();
        Log.d(TAG, "test_001: checked" + checked);*/

        UiObject2 switchobjct = mDevice.findObject(By.clazz(RelativeLayout.class).hasChild(By.text("Turn mute"))).getParent().findObject(By.clazz(Switch.class));
        boolean checked = switchobjct.isChecked();
        Log.d(TAG, "test_001: checked" + checked);


    }
}
