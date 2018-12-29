package com.example.chenghao.xmlpraser;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telecom.TelecomManager;
import android.util.Log;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "anMainActivity";
    private static final String FILE_NAME = "/uiautoconfig.xml";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        //启动后台监听服务
        /*Intent intent = new Intent();
        intent.setAction(ACTION);
        intent.setPackage(getPackageName());
        context.startService(intent);*/
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    public void myconnect(View v) {
        Log.i(TAG, "connectaaa: ");
        TelecomManager telecomManager = (TelecomManager) context.getSystemService(context.TELECOM_SERVICE);
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
}
