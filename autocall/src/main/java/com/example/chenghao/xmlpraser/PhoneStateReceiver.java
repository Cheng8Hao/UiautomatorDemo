package com.example.chenghao.xmlpraser;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static android.content.Context.TELECOM_SERVICE;

/**
 * Created by chenghao on 18-12-6.
 */

public class PhoneStateReceiver extends BroadcastReceiver {
    private static final String TAG = "PhoneStateReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, "PhoneStateReceiver action: " + action);

        String resultData = this.getResultData();
        Log.d(TAG, "PhoneStateReceiver getResultData: " + resultData);

        if (action.equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            // 去电，可以用定时挂断
            // 双卡的手机可能不走这个Action
            String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Log.d(TAG, "PhoneStateReceiver EXTRA_PHONE_NUMBER: " + phoneNumber);
        } else {
            // 来电去电都会走
            // 获取当前电话状态
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            Log.d(TAG, "PhoneStateReceiver onReceive state: " + state);
            // 获取电话号码
            String extraIncomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            Log.d(TAG, "PhoneStateReceiver onReceive extraIncomingNumber: " + extraIncomingNumber);
            //自动接电话
            {
                TelecomManager telecomManager = (TelecomManager) context.getSystemService(TELECOM_SERVICE);
                Method method = null;
                try {
                    method = Class.forName("android.telecom.TelecomManager").getMethod("acceptRingingCall");
                    method.invoke(telecomManager);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }


            if (state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING)) {
                Log.d(TAG, "PhoneStateReceiver onReceive endCall");
                //HangUpTelephonyUtil.endCall(context);
            }
        }
    }

}
