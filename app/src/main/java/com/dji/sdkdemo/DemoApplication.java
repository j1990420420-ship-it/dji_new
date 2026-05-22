package com.dji.sdkdemo;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.secneo.sdk.Helper;

import dji.sdk.sdkmanager.DJISDKManager;
import dji.sdk.sdkmanager.DJIError;
import dji.sdk.sdkmanager.DJISDKInitEvent;
import dji.common.bus.UXSDKEventBusFactory;
import dji.common.error.DJISDKError;
import dji.sdk.base.BaseComponent;
import dji.sdk.base.BaseProduct;

public class DemoApplication extends Application {

    public static final String FLAG_CONNECTION_CHANGE = "flag_connection_change";
    private static DemoApplication mInstance;
    protected DJISDKManager.SDKManagerCallback mDJISDKManagerCallback;
    private Handler mHandler;

    public DemoApplication() {

    }

    public static DemoApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mHandler = new Handler(Looper.getMainLooper());

        // Check the permissions before registering the application
        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), 
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheck2 = ContextCompat.checkSelfPermission(getApplicationContext(), 
                android.Manifest.permission.READ_PHONE_STATE);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || 
                (permissionCheck == 0 && permissionCheck2 == 0)) {
            startSDKRegistration();
        } else {
            Toast.makeText(getApplicationContext(), 
                    "Please check if the permission is granted.", 
                    Toast.LENGTH_LONG).show();
        }
    }

    private void startSDKRegistration() {
        mDJISDKManagerCallback = new DJISDKManager.SDKManagerCallback() {

            @Override
            public void onRegister(DJIError error) {
                if (error == DJISDKError.REGISTRATION_SUCCESS) {
                    DJISDKManager.getInstance().startConnectionToProduct();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), 
                                    "Register Success", 
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                    Log.e("DJI SDK", "Register Success");
                } else {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), 
                                    "Register Failed, check network is available", 
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                    Log.e("DJI SDK", error.toString());
                }
            }

            @Override
            public void onProductDisconnect() {
                Log.d("DJI SDK", "onProductDisconnect");
                notifyStatusChange();
            }

            @Override
            public void onProductConnect(BaseProduct baseProduct) {
                Log.d("DJI SDK", String.format("onProductConnect newProduct:%s", baseProduct));
                notifyStatusChange();
            }

            @Override
            public void onComponentChange(BaseProduct.ComponentKey componentKey, 
                    BaseComponent oldComponent, BaseComponent newComponent) {
                if (newComponent != null) {
                    newComponent.setComponentListener(new BaseComponent.ComponentListener() {
                        @Override
                        public void onConnectivityChange(boolean isConnected) {
                            Log.d("DJI SDK", "onComponentConnectivityChanged: " + isConnected);
                            notifyStatusChange();
                        }
                    });
                }

                Log.d("DJI SDK",
                        String.format("onComponentChange key:%s, oldComponent:%s, newComponent:%s",
                                componentKey, oldComponent, newComponent));
            }

            @Override
            public void onInitProcess(DJISDKInitEvent djisdkInitEvent, int i) {
                Log.d("DJI SDK", "SDK Init Progress: " + djisdkInitEvent + " " + i);
            }
        };

        // Register the app with DJI
        DJISDKManager.getInstance().registerApp(getApplicationContext(), mDJISDKManagerCallback);
    }

    public void notifyStatusChange() {
        UXSDKEventBusFactory.getInstance().post(new Object());
    }

    public void setContext(Context context) {
        attachBaseContext(context);
    }
}
