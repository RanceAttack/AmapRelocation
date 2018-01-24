package com.amap.location.demo;

import android.app.Activity;
import android.net.wifi.WifiInfo;

import com.amap.api.location.AMapLocation;

import java.util.Calendar;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by somehui on 2018/1/23.
 */

public class XposedHook implements IXposedHookLoadPackage {
    private static final String PACKAGE = "com.alibaba.android.rimet";
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if(!lpparam.packageName.equals(PACKAGE)){
            return;
        }
        XposedBridge.log("ttgl Loaded app: " + lpparam.packageName);
        hookF(lpparam,"getLongitude"     );
        hookF(lpparam,"getLatitude"      );
        hookF(lpparam,"getCountry"       );
        hook(lpparam,"getProvince"      );
        hook(lpparam,"getCity"          );
        hook(lpparam,"getCityCode"      );
        hook(lpparam,"getDistrict"      );
        hook(lpparam,"getAdCode"        );
        hook(lpparam,"getAddress"       );
        hook(lpparam,"getBuildingId"    );
        hook(lpparam,"getDescription"   );
        hook(lpparam,"getDistrict"      );
        hook(lpparam,"getLocationDetail");
        hook(lpparam,"getStreet"        );
        hook(lpparam,"getStreetNum"     );
        hook(lpparam,"getAoiName"       );
        final Class<?> mClass = XposedHelpers.findClass(android.net.wifi.WifiInfo.class.getName(), lpparam.classLoader);

        findAndHookMethod(mClass, "getBSSID" , new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Calendar c = Calendar.getInstance();
                if(c.get(Calendar.HOUR_OF_DAY)<9){
                    return;
                }
                XSharedPreferences pre = new XSharedPreferences("com.amap.location.demo", Location_Activity.SP);
                String value = pre.getString("getBSSID","");
                if(!value.isEmpty()){
                    param.setResult(value);
                }
                super.afterHookedMethod(param);
            }
        });

        findAndHookMethod(mClass, "getBSSID" , new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Calendar c = Calendar.getInstance();
                if(c.get(Calendar.HOUR_OF_DAY)<9){
                    return;
                }
                XSharedPreferences pre = new XSharedPreferences("com.amap.location.demo", Location_Activity.SP);
                String value = pre.getString("getBSSID","");
                if(!value.isEmpty()){
                    param.setResult(value);
                }
                super.afterHookedMethod(param);
            }
        });
    }

    private void hook(final XC_LoadPackage.LoadPackageParam lpparam,final String methodName){
        final Class<?> mClass = XposedHelpers.findClass(AMapLocation.class.getName(), lpparam.classLoader);

        findAndHookMethod(mClass, methodName, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Calendar c = Calendar.getInstance();
                if(c.get(Calendar.HOUR_OF_DAY)<9){
                    return;
                }
                XSharedPreferences pre = new XSharedPreferences("com.amap.location.demo", Location_Activity.SP);
                String value = pre.getString(methodName,"");
                if(!value.isEmpty()){
                    XposedBridge.log("newstring: " + value);
                    param.setResult(value);
                }
                super.afterHookedMethod(param);
            }
        });
    }

    private void hookF(final XC_LoadPackage.LoadPackageParam lpparam,final String methodName){
        final Class<?> mClass = XposedHelpers.findClass(AMapLocation.class.getName(), lpparam.classLoader);

        findAndHookMethod(mClass, methodName, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Calendar c = Calendar.getInstance();
                if(c.get(Calendar.HOUR_OF_DAY)<9){
                    return;
                }
                XSharedPreferences pre = new XSharedPreferences("com.amap.location.demo", Location_Activity.SP);
                Float value = pre.getFloat(methodName,-999);
                if(value>-999){
                    XposedBridge.log("newfloat: " + value);
                    param.setResult(value);
                }
                super.afterHookedMethod(param);
            }
        });
    }
}
